package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.recipe.service.DietTypeService;
import com.culinary.userservice.user.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

@ActiveProfiles("test")
public class DietTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DietTypeService dietTypeService;

    @InjectMocks
    private DietTypeController dietTypeController;
    private PodamFactory podamFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(new DietTypeController(dietTypeService)).build();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testGetAllDietTypes() throws Exception {
        List<DietTypeDTO> dietTypeList = IntStream.range(0, 5)
                .mapToObj(i -> podamFactory.manufacturePojo(DietTypeDTO.class))
                .collect(Collectors.toList());
        when(dietTypeService.getAllDietTypes()).thenReturn(dietTypeList);

        mockMvc.perform(get("/api/diet-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dietTypeId").isNotEmpty())
                .andExpect(jsonPath("$[1].dietTypeId").isNotEmpty());

        verify(dietTypeService, times(1)).getAllDietTypes();
    }

    @Test
    public void testGetDietTypeById() throws Exception {
        DietTypeDTO dietTypeDTO = podamFactory.manufacturePojo(DietTypeDTO.class);
        Long dietTypeId = dietTypeDTO.dietTypeId();
        when(dietTypeService.getDietTypeById(dietTypeId)).thenReturn(dietTypeDTO);

        mockMvc.perform(get("/api/diet-types/{id}", dietTypeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dietTypeId").value(dietTypeId));

        verify(dietTypeService, times(1)).getDietTypeById(dietTypeId);
    }

    @Test
    public void testGetDietTypeByIdNotFound() throws Exception {
        Long dietTypeId = 1L;
        when(dietTypeService.getDietTypeById(dietTypeId)).thenThrow(new NotFoundException("Diet type not found"));

        mockMvc.perform(get("/api/diet-types/{id}", dietTypeId))
                .andExpect(status().isNotFound());

        verify(dietTypeService, times(1)).getDietTypeById(dietTypeId);
    }

    @Test
    public void testCreateDietType() throws Exception {
        DietTypeDTO dietTypeDTO = podamFactory.manufacturePojo(DietTypeDTO.class);
        when(dietTypeService.createDietType(any())).thenReturn(dietTypeDTO);

        mockMvc.perform(post("/api/diet-types")
                        .contentType("application/json")
                        .content("{\"name\":\"New Diet Type\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dietTypeId").isNotEmpty());

        verify(dietTypeService, times(1)).createDietType(any());
    }

    @Test
    public void testUpdateDietType() throws Exception {
        DietTypeDTO dietTypeDTO = podamFactory.manufacturePojo(DietTypeDTO.class);
        Long dietTypeId = dietTypeDTO.dietTypeId();
        when(dietTypeService.updateDietType(eq(dietTypeId), any())).thenReturn(dietTypeDTO);

        mockMvc.perform(put("/api/diet-types/{id}", dietTypeId)
                        .contentType("application/json")
                        .content("{\"name\":\"Updated Diet Type\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dietTypeId").value(dietTypeId));

        verify(dietTypeService, times(1)).updateDietType(eq(dietTypeId), any());
    }

    @Test
    public void testDeleteDietType() throws Exception {
        Long dietTypeId = 1L;
        doNothing().when(dietTypeService).deleteDietType(dietTypeId);

        mockMvc.perform(delete("/api/diet-types/{id}", dietTypeId))
                .andExpect(status().isNoContent());

        verify(dietTypeService, times(1)).deleteDietType(dietTypeId);
    }
}
