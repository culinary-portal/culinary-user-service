package com.culinary.userservice.user.service;

import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminService adminService;

    private EasyRandom generator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        generator = new EasyRandom();
    }

    @Test
    void deleteUser() {
        long userId = 1L;

        adminService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void blockUser() {
        User user = generator.nextObject(User.class);
        user.setLocked(false);
        long userId = user.getId();

        when(userService.getUserEntityById(userId)).thenReturn(user);

        adminService.blockUser(userId);

        verify(userService, times(1)).getUserEntityById(userId);
        verify(userRepository, times(1)).save(user);
        assert (user.isLocked());
    }

    @Test
    void unblockUser() {
        User user = generator.nextObject(User.class);
        user.setLocked(true);
        long userId = user.getId();

        when(userService.getUserEntityById(userId)).thenReturn(user);

        adminService.unblockUser(userId);

        verify(userService, times(1)).getUserEntityById(userId);
        verify(userRepository, times(1)).save(user);
        assert (!user.isLocked());
    }
}
