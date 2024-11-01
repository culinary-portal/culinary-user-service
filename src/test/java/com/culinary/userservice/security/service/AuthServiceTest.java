package com.culinary.userservice.security.service;

import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.security.dto.RegisterDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.exception.UserAlreadyExistsException;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import com.culinary.userservice.user.service.UserService;
import com.culinary.userservice.util.ObjectFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityContextRepository securityContextRepository;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private UserDetails userDetails;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister_NewCustomer_Success() {
        RegisterDTO dto = new RegisterDTO("username", "test@example.com", "password");
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(dto.password())).thenReturn(encodedPassword);

        UserDetailsDTO result = authService.register(dto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testRegister_ExistingCustomer_ExceptionThrown() {
        RegisterDTO dto = new RegisterDTO("username", "test@example.com", "password");
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testLogin() {
        AuthDTO dto = new AuthDTO("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");


        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(userService.getLoggedUser()).thenReturn(ObjectFactory.createTestUser());

        authService.login(dto, request, response);

        verify(authManager, times(1)).authenticate(any());
        verify(securityContextRepository, times(1)).saveContext(any(), eq(request), eq(response));
    }
}
