package com.culinary.userservice.user.service;

import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.exception.UserNotFoundException;
import com.culinary.userservice.user.mapper.UserMapper;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        return user.get();

    }

    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<UserNoDetailsDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserNoDetailsDTO)
                .collect(Collectors.toList());
    }
}
