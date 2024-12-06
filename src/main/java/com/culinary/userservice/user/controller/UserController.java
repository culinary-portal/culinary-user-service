package com.culinary.userservice.user.controller;

import com.culinary.userservice.user.dto.PutUserDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserDetailsById(@PathVariable @Parameter(description = "Id of the User to get") long userId) {
        UserDetailsDTO user = userService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/username/{userName}")
    public UserDetailsDTO getUserByUserName(@PathVariable String userName) {
        return userService.getUserByUserName(userName);
    }

    @PutMapping("/{userId}")
    public UserDetailsDTO updateUser(@PathVariable long userId, @RequestBody PutUserDTO putUserDTO) {
        return userService.updateUser(userId, putUserDTO);
    }

}