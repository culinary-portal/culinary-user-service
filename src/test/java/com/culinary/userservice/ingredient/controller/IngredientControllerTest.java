package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.IngredientDTO;
import com.culinary.userservice.ingredient.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@EnabledIfEnvironmentVariable(named = "INTEGRATION_ENABLED", matches = "true")
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IngredientService ingredientService;

    @Test
    void getAllIngredients() throws Exception {
        List<IngredientDTO> ingredients = Arrays.asList(
                IngredientDTO.builder().ingredientId(1).name("Tomato").build(),
                IngredientDTO.builder().ingredientId(2).name("Basil").build()
        );

        given(ingredientService.findAll()).willReturn(ingredients);

        mockMvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getIngredientById() throws Exception {
        IngredientDTO ingredient = IngredientDTO.builder().ingredientId(1).name("Tomato").build();

        given(ingredientService.findById(1)).willReturn(ingredient);

        mockMvc.perform(get("/api/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createIngredient() throws Exception {
        IngredientDTO ingredientDto = IngredientDTO.builder().ingredientId(1).name("Carrot").build();
        IngredientDTO createdIngredient = IngredientDTO.builder().ingredientId(2).name("Tomato").build();

        given(ingredientService.save(ingredientDto)).willReturn(createdIngredient);

        mockMvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientDto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateIngredient() throws Exception {
        IngredientDTO ingredientDto = IngredientDTO.builder().ingredientId(1).name("Tomato").build();
        IngredientDTO updatedIngredient = IngredientDTO.builder().ingredientId(1).name("Bacon").build();

        given(ingredientService.update(1, ingredientDto)).willReturn(updatedIngredient);

        mockMvc.perform(put("/api/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteIngredient() throws Exception {
        mockMvc.perform(delete("/api/ingredients/1"))
                .andExpect(status().isOk());
    }
}