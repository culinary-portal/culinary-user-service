package com.culinary.userservice.security.controller;

import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.security.dto.RegisterDTO;
import com.culinary.userservice.security.service.AuthService;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return ResponseEntity
                .status(CREATED)
                .body(authService.register(registerDTO));
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

    @PutMapping("/password/{id}")
    public String updatePassword(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, @RequestBody String password) {
        return authService.changePassword(request, response, id, password);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok().build();
    }
}
