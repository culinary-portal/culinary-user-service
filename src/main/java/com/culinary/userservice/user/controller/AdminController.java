package com.culinary.userservice.user.controller;

import com.culinary.userservice.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PutMapping("{userId}/block")
    public ResponseEntity<Void> blockUser(@PathVariable long userId) {
        adminService.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{userId}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable long userId) {
        adminService.unblockUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
