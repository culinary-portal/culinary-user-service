package com.culinary.userservice.user.controller;

import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserPreferencesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserPreferencesController userPreferencesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userPreferencesController).build();
    }

    @Test
    void getFavoriteRecipes() throws Exception {
        long userId = 1L;
        Set<GeneralRecipeViewDTO> favoriteRecipes = Set.of();
        when(userService.getFavoriteRecipes(userId)).thenReturn(favoriteRecipes);

        mockMvc.perform(get("/api/user/{userId}/favorite-recipes", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getFavoriteDiets() throws Exception {
        long userId = 1L;
        Set<DietTypeDTO> favoriteDiets = Set.of();
        when(userService.getFavoriteDiets(userId)).thenReturn(favoriteDiets);

        mockMvc.perform(get("/api/user/{userId}/favorite-diets", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addFavoriteRecipe() throws Exception {
        long userId = 1L;
        long recipeId = 100L;
        Set<GeneralRecipeViewDTO> favoriteRecipes = Set.of();
        when(userService.addFavoriteRecipe(userId, recipeId)).thenReturn(favoriteRecipes);

        mockMvc.perform(post("/api/user/{userId}/favorite-recipes/{recipeId}", userId, recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addFavoriteDiet() throws Exception {
        long userId = 1L;
        long dietId = 10L;
        Set<DietTypeDTO> favoriteDiets = Set.of();
        when(userService.addFavoriteDiet(userId, dietId)).thenReturn(favoriteDiets);

        mockMvc.perform(post("/api/user/{userId}/favorite-diets/{dietId}", userId, dietId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFavoriteRecipe() throws Exception {
        long userId = 1L;
        long recipeId = 100L;
        Set<GeneralRecipeViewDTO> favoriteRecipes = Set.of();
        when(userService.deleteFavoriteRecipe(userId, recipeId)).thenReturn(favoriteRecipes);

        mockMvc.perform(delete("/api/user/{userId}/favorite-recipes/{recipeId}", userId, recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFavoriteDiet() throws Exception {
        long userId = 1L;
        long dietId = 10L;
        Set<DietTypeDTO> favoriteDiets = Set.of();
        when(userService.deleteFavoriteDiet(userId, dietId)).thenReturn(favoriteDiets);

        mockMvc.perform(delete("/api/user/{userId}/favorite-diets/{dietId}", userId, dietId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
