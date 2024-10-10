package com.culinary.userservice.security.controller;

import com.culinary.userservice.CulinaryUserApplication;
import com.culinary.userservice.security.dto.AuthDTO;
import com.culinary.userservice.security.service.AuthService;
import com.culinary.userservice.user.exception.UserAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CulinaryUserApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class AuthControllerTest {

    @MockBean
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register() throws Exception {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        AuthDTO authDTO = new AuthDTO(generatedString, "testReg");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void registerWithConflict() throws Exception {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        AuthDTO authDTO = new AuthDTO(generatedString, "testReg");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        when(authService.register(any())).thenThrow(new UserAlreadyExistsException("User already exists"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void loginUser() throws Exception {
        AuthDTO authDTO = new AuthDTO("test@email", "test");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
