package com.culinary.userservice.user.controller;

import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserNoDetailsDTO>> checkAuthentication(Authentication authentication) {
        List<UserNoDetailsDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetailsById(@PathVariable @Parameter(description = "ID of the User to get") long id) {
        UserDetailsDTO user = userService.getUserDetails(id);
        return ResponseEntity.ok(user);
    }
}