package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.general.GetGeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.PutGeneralRecipeDTO;
import com.culinary.userservice.recipe.service.GeneralRecipeService;
import com.culinary.userservice.user.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class GeneralRecipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GeneralRecipeService generalRecipeService;

    @InjectMocks
    private GeneralRecipeController generalRecipeController;

    private PodamFactory podamFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(new GeneralRecipeController(generalRecipeService)).build();
        podamFactory = new PodamFactoryImpl();
    }

//    @Test
//    public void testGetAllGeneralRecipesByFiltered() throws Exception {
//        List<GetGeneralRecipeDTO> recipeList = IntStream.range(0, 5)
//                .mapToObj(i -> podamFactory.manufacturePojo(GetGeneralRecipeDTO.class))
//                .collect(Collectors.toList());
//
//        Page<GetGeneralRecipeDTO> recipePage = new PageImpl<>(recipeList);
//
//        when(generalRecipeService.getFilteredGeneralRecipes(any(), any(), any(), any(), any(), any()))
//                .thenReturn(recipePage);
//
//        mockMvc.perform(get("/api/general-recipes")
//                        .param("page", "0")
//                        .param("size", "5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.content.length()").value(5))
//                .andExpect(jsonPath("$.content[0].generalRecipeId").isNotEmpty())
//                .andExpect(jsonPath("$.content[1].generalRecipeId").isNotEmpty());
//
//        verify(generalRecipeService, times(1))
//                .getFilteredGeneralRecipes(any(), any(), any(), any(), any(), any());
//    }


    @Test
    public void testGetGeneralRecipeById() throws Exception {
        GetGeneralRecipeDTO recipeDTO = podamFactory.manufacturePojo(GetGeneralRecipeDTO.class);
        Long recipeId = recipeDTO.generalRecipeId();
        when(generalRecipeService.getGeneralRecipeById(recipeId)).thenReturn(recipeDTO);

        mockMvc.perform(get("/api/general-recipes/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generalRecipeId").value(recipeId));

        verify(generalRecipeService, times(1)).getGeneralRecipeById(recipeId);
    }

    @Test
    public void testGetGeneralRecipeByIdNotFound() throws Exception {
        Long recipeId = 1L;
        when(generalRecipeService.getGeneralRecipeById(recipeId)).thenThrow(new NotFoundException("Recipe not found"));

        mockMvc.perform(get("/api/general-recipes/{id}", recipeId))
                .andExpect(status().isNotFound());

        verify(generalRecipeService, times(1)).getGeneralRecipeById(recipeId);
    }

    @Test
    public void testCreateGeneralRecipe() throws Exception {
        GetGeneralRecipeDTO recipeDTO = podamFactory.manufacturePojo(GetGeneralRecipeDTO.class);
        PutGeneralRecipeDTO putRecipeDTO = podamFactory.manufacturePojo(PutGeneralRecipeDTO.class);
        when(generalRecipeService.createGeneralRecipe(any())).thenReturn(recipeDTO);

        mockMvc.perform(post("/api/general-recipes")
                        .contentType("application/json")
                        .content("{\"name\":\"New Recipe\",\"mealType\":\"Dinner\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.generalRecipeId").isNotEmpty());

        verify(generalRecipeService, times(1)).createGeneralRecipe(any());
    }

    @Test
    public void testUpdateGeneralRecipe() throws Exception {
        GetGeneralRecipeDTO recipeDTO = podamFactory.manufacturePojo(GetGeneralRecipeDTO.class);
        Long recipeId = recipeDTO.generalRecipeId();
        when(generalRecipeService.updateGeneralRecipe(eq(recipeId), any())).thenReturn(recipeDTO);

        mockMvc.perform(put("/api/general-recipes/{id}", recipeId)
                        .contentType("application/json")
                        .content("{\"name\":\"Updated Recipe\",\"mealType\":\"Lunch\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generalRecipeId").value(recipeId));

        verify(generalRecipeService, times(1)).updateGeneralRecipe(eq(recipeId), any());
    }

    @Test
    public void testDeleteGeneralRecipe() throws Exception {
        Long recipeId = 1L;
        doNothing().when(generalRecipeService).deleteGeneralRecipe(recipeId);

        mockMvc.perform(delete("/api/general-recipes/{id}", recipeId))
                .andExpect(status().isNoContent());

        verify(generalRecipeService, times(1)).deleteGeneralRecipe(recipeId);
    }
}
