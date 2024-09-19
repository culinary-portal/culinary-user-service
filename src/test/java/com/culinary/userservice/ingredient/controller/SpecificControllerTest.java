package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.specific.PutSpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.SpecificDTO;
import com.culinary.userservice.ingredient.service.SpecificService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ActiveProfiles("integration")
@EnabledIfEnvironmentVariable(named = "INTEGRATION_ENABLED", matches = "true")
public class SpecificControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SpecificService specificService;

    @InjectMocks
    private SpecificController specificController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(new SpecificController(specificService)).build();
    }

    @Test
    public void testGetAllSpecifics() throws Exception {
        List<SpecificDTO> specificList = Arrays.asList(
                new SpecificDTO(1L, 1L, 1L, true),
                new SpecificDTO(2L, 1L, 1L, true)
        );
        when(specificService.findAll()).thenReturn(specificList);

        mockMvc.perform(get("/api/specifics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specificId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Specific1"))
                .andExpect(jsonPath("$[1].specificId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Specific2"));

        verify(specificService, times(1)).findAll();
    }

    @Test
    public void testGetSpecificById() throws Exception {
        long specificId = 1L;
        SpecificDTO specificDTO = new SpecificDTO(1L, 1L, 1L, true);
        when(specificService.findById(specificId)).thenReturn(specificDTO);

        mockMvc.perform(get("/api/specifics/{id}", specificId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(specificId))
                .andExpect(jsonPath("$.name").value("Specific1"));

        verify(specificService, times(1)).findById(specificId);
    }

    @Test
    public void testCreateSpecific() throws Exception {
        PutSpecificDTO putSpecificDTO = new PutSpecificDTO(1L, 1L, true);
        SpecificDTO specificDTO = new SpecificDTO(1L, 1L, 1L, true);
        when(specificService.save(any(PutSpecificDTO.class))).thenReturn(specificDTO);

        mockMvc.perform(post("/api/specifics")
                        .contentType("application/json")
                        .content("{\"name\": \"Specific1\", \"description\": \"Description1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Specific1"));

        verify(specificService, times(1)).save(any(PutSpecificDTO.class));
    }

    @Test
    public void testDeleteSpecific() throws Exception {
        long specificId = 1L;
        doNothing().when(specificService).delete(specificId);

        mockMvc.perform(delete("/api/specifics/{id}", specificId))
                .andExpect(status().isOk());

        verify(specificService, times(1)).delete(specificId);
    }
}