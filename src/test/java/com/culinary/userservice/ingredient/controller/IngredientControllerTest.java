package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import com.culinary.userservice.ingredient.dto.ingredient.PutIngredientDTO;
import com.culinary.userservice.ingredient.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class IngredientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController ingredientController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(new IngredientController(ingredientService)).build();
    }

    @Test
    public void testGetAllIngredients() throws Exception {
        List<IngredientDTO> ingredientList = Arrays.asList(
                new IngredientDTO(1L, "Salt", 1.0, 1.0, 1.0, 1.0, true, true),
                new IngredientDTO(2L, "Sugar", 1.0, 1.0, 1.0, 1.0, true, true)
        );
        when(ingredientService.findAll()).thenReturn(ingredientList);

        mockMvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ingredientId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Salt"))
                .andExpect(jsonPath("$[1].ingredientId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Sugar"));

        verify(ingredientService, times(1)).findAll();
    }

    @Test
    public void testGetIngredientById() throws Exception {
        long ingredientId = 1L;
        IngredientDTO ingredientDTO = new IngredientDTO(ingredientId, "Salt", 1.0, 1.0, 1.0, 1.0, true, true);
        when(ingredientService.findById(ingredientId)).thenReturn(ingredientDTO);

        mockMvc.perform(get("/api/ingredients/{id}", ingredientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId").value(ingredientId))
                .andExpect(jsonPath("$.name").value("Salt"));

        verify(ingredientService, times(1)).findById(ingredientId);
    }

    @Test
    public void testCreateIngredient() throws Exception {
        PutIngredientDTO putIngredientDTO = new PutIngredientDTO("Salt", 1.0,
                1.0, 1.0, 1.0, true, true);
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Salt", 1.0, 1.0, 1.0, 1.0, true, true);
        when(ingredientService.save(any(PutIngredientDTO.class))).thenReturn(ingredientDTO);

        mockMvc.perform(post("/api/ingredients")
                        .contentType("application/json")
                        .content("{\"name\": \"Salt\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ingredientId").value(1L))
                .andExpect(jsonPath("$.name").value("Salt"));

        verify(ingredientService, times(1)).save(any(PutIngredientDTO.class));
    }

    @Test
    public void testUpdateIngredient() throws Exception {

        long ingredientId = 1L;
        PutIngredientDTO putIngredientDTO = new PutIngredientDTO("Updated Salt", 1.0, 1.0, 1.0, 1.0, true, true);
        IngredientDTO updatedIngredientDTO = new IngredientDTO(ingredientId, "Salt", 1.0, 1.0, 1.0, 1.0, true, true);
        when(ingredientService.update(eq(ingredientId), any(PutIngredientDTO.class))).thenReturn(updatedIngredientDTO);

        mockMvc.perform(put("/api/ingredients/{id}", ingredientId)
                        .contentType("application/json")
                        .content("{\"name\": \"Updated Salt\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId").value(ingredientId))
                .andExpect(jsonPath("$.name").value("Salt"));

        verify(ingredientService, times(1)).update(eq(ingredientId), any(PutIngredientDTO.class));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        long ingredientId = 1L;
        doNothing().when(ingredientService).delete(ingredientId);

        mockMvc.perform(delete("/api/ingredients/{id}", ingredientId))
                .andExpect(status().isNoContent());

        verify(ingredientService, times(1)).delete(ingredientId);
    }
}
