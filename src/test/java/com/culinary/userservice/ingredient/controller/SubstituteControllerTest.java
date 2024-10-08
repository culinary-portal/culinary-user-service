package com.culinary.userservice.ingredient.controller;
import com.culinary.userservice.ingredient.dto.substitute.GetSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.PutSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.SubstituteDTO;
import com.culinary.userservice.ingredient.service.SubstituteService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ActiveProfiles("integration")
class SubstituteControllerTest {

    private MockMvc mockMvc;
    private PodamFactory podamFactory;

    @Mock
    private SubstituteService substituteService;

    @InjectMocks
    private SubstituteController substituteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(substituteController).build();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void getAllSubstitutes() throws Exception {
        List<SubstituteDTO> substitutes = IntStream.range(0, 5)
                .mapToObj(i -> podamFactory.manufacturePojo(SubstituteDTO.class))
                .collect(Collectors.toList());

        when(substituteService.findAll()).thenReturn(substitutes);

        mockMvc.perform(get("/api/substitutes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(substitutes.size()));

        verify(substituteService, times(1)).findAll();
    }

    @Test
    void getSubstituteById() throws Exception {
        GetSubstituteDTO substituteDTO = podamFactory.manufacturePojo(GetSubstituteDTO.class);
        when(substituteService.findById(anyLong())).thenReturn(substituteDTO);

        mockMvc.perform(get("/api/substitutes/{id}", substituteDTO.substituteId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.substituteId").value(substituteDTO.substituteId()));

        verify(substituteService, times(1)).findById(anyLong());
    }

    @Test
    void createSubstitute() throws Exception {
        SubstituteDTO substituteDTO = podamFactory.manufacturePojo(SubstituteDTO.class);
        PutSubstituteDTO putSubstituteDTO = podamFactory.manufacturePojo(PutSubstituteDTO.class);

        when(substituteService.save(any(PutSubstituteDTO.class))).thenReturn(substituteDTO);

        mockMvc.perform(post("/api/substitutes")
                        .contentType("application/json")
                        .content("{ \"ingredient1\": {\"id\": 1}, \"ingredient2\": {\"id\": 2}, \"proportionI1I2\": 1.5 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.substituteId").value(substituteDTO.substituteId()));

        verify(substituteService, times(1)).save(any(PutSubstituteDTO.class));
    }

    @Test
    void updateSubstitute() throws Exception {
        SubstituteDTO substituteDTO = podamFactory.manufacturePojo(SubstituteDTO.class);
        PutSubstituteDTO putSubstituteDTO = podamFactory.manufacturePojo(PutSubstituteDTO.class);

        when(substituteService.update(anyLong(), any(PutSubstituteDTO.class))).thenReturn(substituteDTO);

        mockMvc.perform(put("/api/substitutes/{id}", substituteDTO.substituteId())
                        .contentType("application/json")
                        .content("{ \"ingredient1\": {\"id\": 1}, \"ingredient2\": {\"id\": 2}, \"proportionI1I2\": 1.5 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.substituteId").value(substituteDTO.substituteId()));

        verify(substituteService, times(1)).update(anyLong(), any(PutSubstituteDTO.class));
    }

    @Test
    void deleteSubstitute() throws Exception {
        long substituteId = 1L;
        doNothing().when(substituteService).delete(substituteId);

        mockMvc.perform(delete("/api/substitutes/{id}", substituteId))
                .andExpect(status().isNoContent());

        verify(substituteService, times(1)).delete(substituteId);
    }

    @Test
    void getSubstituteByIngredientId() throws Exception {
        List<GetSubstituteDTO> substitutes = IntStream.range(0, 3)
                .mapToObj(i -> podamFactory.manufacturePojo(GetSubstituteDTO.class))
                .collect(Collectors.toList());

        when(substituteService.findSubstituteByIngredientId(anyLong())).thenReturn(substitutes);

        mockMvc.perform(get("/api/substitutes/ingredient")
                        .param("ingredientId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(substitutes.size()));

        verify(substituteService, times(1)).findSubstituteByIngredientId(anyLong());
    }
}
