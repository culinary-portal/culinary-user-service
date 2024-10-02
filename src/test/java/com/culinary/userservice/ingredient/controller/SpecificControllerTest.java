package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.specific.SpecificDTO;
import com.culinary.userservice.ingredient.service.SpecificService;
import com.culinary.userservice.user.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ActiveProfiles("integration")
public class SpecificControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SpecificService specificService;

    @InjectMocks
    private SpecificController specificController;
    private PodamFactory podamFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(new SpecificController(specificService)).build();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testGetAllSpecifics() throws Exception {
        List<SpecificDTO> specificList = IntStream.range(0, 5)
                .mapToObj(i -> podamFactory.manufacturePojo(SpecificDTO.class))
                .collect(Collectors.toList());
        when(specificService.findAll()).thenReturn(specificList);

        mockMvc.perform(get("/api/user/specific"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specificId").isNotEmpty())
                .andExpect(jsonPath("$[1].specificId").isNotEmpty());

        verify(specificService, times(1)).findAll();
    }

    @Test
    public void testGetSpecificById() throws Exception {
        SpecificDTO specificDTO = podamFactory.manufacturePojo(SpecificDTO.class);
        Long specificId = specificDTO.specificId();
        when(specificService.findById(specificId)).thenReturn(specificDTO);

        mockMvc.perform(get("/api/user/specific/{id}", specificId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specificId").value(specificId));

        verify(specificService, times(1)).findById(specificId);
    }

    @Test
    public void testGetSpecificByIdNotFound() throws Exception {
        Long specificId = 1L;
        when(specificService.findById(specificId)).thenThrow(new NotFoundException("Test"));

        mockMvc.perform(get("/api/user/specific/{id}", specificId))
                .andExpect(status().isNotFound());

        verify(specificService, times(1)).findById(specificId);
    }

    @Test
    public void testDeleteSpecific() throws Exception {
        Long specificId = 1L;
        doNothing().when(specificService).delete(specificId);

        mockMvc.perform(delete("/api/user/specific/{id}", specificId))
                .andExpect(status().isNoContent());

        verify(specificService, times(1)).delete(specificId);
    }

    @Test
    public void testCreateSpecific() throws Exception {
        SpecificDTO specificDTO = podamFactory.manufacturePojo(SpecificDTO.class);
        when(specificService.save(any())).thenReturn(specificDTO);

        mockMvc.perform(post("/api/user/specific")
                        .contentType("application/json")
                        .content("{\"specificName\":\"New Specific\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.specificId").isNotEmpty());

        verify(specificService, times(1)).save(any());
    }


}