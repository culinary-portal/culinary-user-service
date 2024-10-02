package com.culinary.userservice.user.model.userdetail;

import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailService userDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void loadUserByUsername() {
        when(userRepository.findByEmail("principal")).thenReturn(Optional.of(new User()));
        UserDetails userDetails = userDetailService.loadUserByUsername("principal");
        assertNotNull(userDetails);

    }

    @Test
    void loadUserByUsername_NotFound() {

        when(userRepository.findByEmail("principal")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("principal"));
    }
}