package com.culinary.userservice.user.service;

import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserService userService;

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void blockUser(long id) {
        User user = userService.getUserEntityById(id);
        user.setLocked(true);
        userRepository.save(user);
    }


    public void unblockUser(long id) {
        User user = userService.getUserEntityById(id);
        user.setLocked(false);
        userRepository.save(user);
    }
}
