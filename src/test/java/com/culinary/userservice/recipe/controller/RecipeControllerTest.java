package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.recipe.GetRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.service.RecipeService;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ActiveProfiles("test")
public class RecipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private PodamFactory podamFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(recipeController).build();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        List<RecipeDTO> recipeList = IntStream.range(0, 5)
                .mapToObj(i -> podamFactory.manufacturePojo(RecipeDTO.class))
                .collect(Collectors.toList());
        when(recipeService.getAllRecipes()).thenReturn(recipeList);

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").isNotEmpty())
                .andExpect(jsonPath("$[1].recipeId").isNotEmpty());

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    public void testGetRecipeById() throws Exception {
        RecipeDetailsDTO recipeDetailsDTO = podamFactory.manufacturePojo(RecipeDetailsDTO.class);
        Long recipeId = recipeDetailsDTO.getRecipeId();
        when(recipeService.getRecipeById(recipeId)).thenReturn(recipeDetailsDTO);

        mockMvc.perform(get("/api/recipes/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipeId));

        verify(recipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    public void testGetRecipeByIdNotFound() throws Exception {
        Long recipeId = 1L;
        when(recipeService.getRecipeById(recipeId)).thenThrow(new NotFoundException("Recipe not found"));

        mockMvc.perform(get("/api/recipes/{id}", recipeId))
                .andExpect(status().isNotFound());

        verify(recipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    public void testCreateRecipe() throws Exception {
        RecipeDTO recipeDTO = podamFactory.manufacturePojo(RecipeDTO.class);
        PutRecipeDTO putRecipeDTO = podamFactory.manufacturePojo(PutRecipeDTO.class);
        when(recipeService.createRecipe(any())).thenReturn(recipeDTO);

        mockMvc.perform(post("/api/recipes")
                        .contentType("application/json")
                        .content("{\"name\":\"New Recipe\",\"ingredients\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recipeId").isNotEmpty());

        verify(recipeService, times(1)).createRecipe(any());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        RecipeDTO recipeDTO = podamFactory.manufacturePojo(RecipeDTO.class);
        Long recipeId = recipeDTO.getRecipeId();
        when(recipeService.updateRecipeDTO(eq(recipeId), any())).thenReturn(recipeDTO);

        mockMvc.perform(put("/api/recipes/{id}", recipeId)
                        .contentType("application/json")
                        .content("{\"name\":\"Updated Recipe\",\"ingredients\":[]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipeId));

        verify(recipeService, times(1)).updateRecipeDTO(eq(recipeId), any());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        Long recipeId = 1L;
        doNothing().when(recipeService).deleteRecipe(recipeId);

        mockMvc.perform(delete("/api/recipes/{id}", recipeId))
                .andExpect(status().isNoContent());

        verify(recipeService, times(1)).deleteRecipe(recipeId);
    }

    @Test
    public void testGetModifications() throws Exception {
        Long userId = 1L;
        Set<GetRecipeDTO> modifications = IntStream.range(0, 3)
                .mapToObj(i -> podamFactory.manufacturePojo(GetRecipeDTO.class))
                .collect(Collectors.toSet());

        when(recipeService.getModifications(userId)).thenReturn(modifications);

        mockMvc.perform(get("/api/recipes/{userId}/modifications", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").isNotEmpty());

        verify(recipeService, times(1)).getModifications(userId);
    }

    @Test
    public void testAddModification() throws Exception {
        Long userId = 1L;
        Set<PutRecipeDTO> modifications = IntStream.range(0, 3)
                .mapToObj(i -> podamFactory.manufacturePojo(PutRecipeDTO.class))
                .collect(Collectors.toSet());
        when(recipeService.addModification(eq(userId), any())).thenReturn(modifications);

        mockMvc.perform(post("/api/recipes/{userId}/modifications", userId)
                        .contentType("application/json")
                        .content("{\"name\":\"New Modification\",\"ingredients\":[]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").isNotEmpty());

        verify(recipeService, times(1)).addModification(eq(userId), any());
    }

    @Test
    public void testDeleteModification() throws Exception {
        Long userId = 1L;
        Long recipeId = 2L;
        Set<PutRecipeDTO> modifications = IntStream.range(0, 2)
                .mapToObj(i -> podamFactory.manufacturePojo(PutRecipeDTO.class))
                .collect(Collectors.toSet());
        when(recipeService.deleteModification(userId, recipeId)).thenReturn(modifications);

        mockMvc.perform(delete("/api/recipes/{userId}/modifications/{recipeId}", userId, recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").isNotEmpty());

        verify(recipeService, times(1)).deleteModification(userId, recipeId);
    }
}
