package com.culinary.userservice.user.controller;

import com.culinary.userservice.user.dto.PutUserDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void checkAuthentication() throws Exception {
        List<UserNoDetailsDTO> users = Collections.singletonList(new UserNoDetailsDTO(1L, "username", "test"));
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUserDetailsById() throws Exception {
        UserDetailsDTO user = new UserDetailsDTO(1L, "email@example.com", "username", "test",
                false, false, false, false, null, null, new HashSet<>());
        when(userService.getUserDetails(1L)).thenReturn(user);

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "id": 1,
                          "email": "email@example.com",
                          "userName": "username",
                          "photoUrl": "test",
                          "enabled": false,
                          "credentialsNonExpired": false,
                          "accountNonExpired": false,
                          "locked": false,
                          "birthdate": null,
                          "createDate": null,
                          "specifics": []
                        }
                        """));
    }

    @Test
    void getUserByUserName() throws Exception {
        UserDetailsDTO user = new UserDetailsDTO(1L, "email@example.com", "username", "test",
                false, false, false, false, null, null, new HashSet<>());
        when(userService.getUserByUserName("username")).thenReturn(user);

        mockMvc.perform(get("/api/user/username/username")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "id": 1,
                          "email": "email@example.com",
                          "userName": "username",
                          "photoUrl": "test",
                          "enabled": false,
                          "credentialsNonExpired": false,
                          "accountNonExpired": false,
                          "locked": false,
                          "birthdate": null,
                          "createDate": null,
                          "specifics": []
                        }
                        """));
    }

    @Test
    void updateUser() throws Exception {
        PutUserDTO putUserDTO = new PutUserDTO("newUsername", "newEmail@example.com", "password", new Date(0));
        UserDetailsDTO updatedUser = new UserDetailsDTO(1L, "newEmail@example.com", "newUsername", "test",
                false, false, false, false, new Date(0), new Date(0), new HashSet<>());
        when(userService.updateUser(1L, putUserDTO)).thenReturn(updatedUser);

        mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userName": "newUsername",
                                  "email": "newEmail@example.com",
                                  "password": "password",
                                  "birthdate": "1970-01-01T00:00:00.000+00:00"
                                }
                                """))
                .andExpect(status().isOk());
    }
}
