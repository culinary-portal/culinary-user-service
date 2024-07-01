package com.culinary.userservice.security.controller;

import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDTO authDTO) {
        return ResponseEntity
                .status(CREATED)
                .body(authService.register(authDTO));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginEmployee(
            @Valid @RequestBody AuthDTO authDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new ResponseEntity<>(authService.login(authDTO, request, response), OK);
    }

    @GetMapping(path = "/user")
    public String checkAuthentication(Authentication authentication) {
        return "An Admin or User can hit this rout. User name is " + authentication.getName();
    }

    @GetMapping(path = "/authenticated")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String getAuthenticated(Authentication authentication) {
        return "Admin name is " + authentication.getName();
    }

    @PutMapping("/password/{id}")
    public String updatePassword(@PathVariable Long id, @RequestBody String password) {
        return authService.changePassword(id, password);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request , response);
        return ResponseEntity.ok().build();
    }
}
