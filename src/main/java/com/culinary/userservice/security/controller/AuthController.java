package com.culinary.userservice.security.controller;

import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.security.dto.RegisterDTO;
import com.culinary.userservice.security.service.AuthService;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        UserDetailsDTO register;
        try {
            register = authService.register(registerDTO);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(e.getMessage());
        }
        return ResponseEntity
                .status(CREATED)
                .body(register);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserDetailsDTO> login(
            @Valid @RequestBody AuthDTO authDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.info("User trying to log in email --> {}", authDTO.email());
        UserDetailsDTO user = authService.login(authDTO, request, response);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/authenticated")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String getAuthenticated(Authentication authentication) {
        return "Admin name is " + authentication.getName();
    }

    @PutMapping("/password/{userId}")
    public String updatePassword(HttpServletRequest request, HttpServletResponse response, @PathVariable Long userId, @RequestBody String password) {
        return authService.changePassword(request, response, userId, password);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok().build();
    }
}
