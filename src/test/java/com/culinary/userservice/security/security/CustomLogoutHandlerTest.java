package com.culinary.userservice.security.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

import static org.mockito.Mockito.*;


class CustomLogoutHandlerTest {

    @Mock
    private FindByIndexNameSessionRepository<? extends Session> redisIndexedSessionRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private CustomLogoutHandler customLogoutHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void logoutWithNullSessionId() {
        when(request.getSession(false)).thenReturn(httpSession);
        when(httpSession.getId()).thenReturn(null);

        customLogoutHandler.logout(request, response, authentication);

        verify(redisIndexedSessionRepository, never()).deleteById(anyString());
    }

}