package com.culinary.userservice.security.controller;

import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
