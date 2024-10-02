package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.general.GeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.GetGeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.PutGeneralRecipeDTO;
import com.culinary.userservice.recipe.mapper.GeneralRecipeMapper;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.repository.GeneralRecipeRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GeneralRecipeServiceTest {

    @Mock
    private GeneralRecipeRepository generalRecipeRepository;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private GeneralRecipeService generalRecipeService;

    private EasyRandom easyRandom;
    private PodamFactoryImpl podamFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        easyRandom = new EasyRandom();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void testCreateGeneralRecipe() {
        PutGeneralRecipeDTO putGeneralRecipeDTO = podamFactory.manufacturePojo(PutGeneralRecipeDTO.class);
        GeneralRecipe generalRecipe = easyRandom.nextObject(GeneralRecipe.class);
        Recipe recipe = easyRandom.nextObject(Recipe.class);
        GetGeneralRecipeDTO expectedDto = podamFactory.manufacturePojo(GetGeneralRecipeDTO.class);

        try (MockedStatic<GeneralRecipeMapper> mockedMapper = mockStatic(GeneralRecipeMapper.class)) {
            when(recipeService.createRecipe(any(), any())).thenReturn(recipe);
            when(generalRecipeRepository.save(any(GeneralRecipe.class))).thenReturn(generalRecipe);
            mockedMapper.when(() -> GeneralRecipeMapper.toGetDTO(any(GeneralRecipe.class))).thenReturn(expectedDto);

            GetGeneralRecipeDTO result = generalRecipeService.createGeneralRecipe(putGeneralRecipeDTO);

            assertNotNull(result);
            assertEquals(expectedDto, result);
            verify(recipeService).createRecipe(any(), any(GeneralRecipe.class));
            verify(generalRecipeRepository).save(any(GeneralRecipe.class));
        }
    }

    @Test
    void testDeleteGeneralRecipe() {
        long id = 1L;

        generalRecipeService.deleteGeneralRecipe(id);

        verify(generalRecipeRepository).deleteById(id);
    }

    @Test
    void testGetGeneralRecipeEntityById() {
        long id = 1L;
        GeneralRecipe generalRecipe = easyRandom.nextObject(GeneralRecipe.class);

        when(generalRecipeRepository.findById(id)).thenReturn(Optional.of(generalRecipe));

        GeneralRecipe result = generalRecipeService.getGeneralRecipeEntityById(id);

        assertNotNull(result);
        verify(generalRecipeRepository).findById(id);
    }

    @Test
    void testGetGeneralRecipeById() {
        long id = 1L;
        GeneralRecipe generalRecipe = easyRandom.nextObject(GeneralRecipe.class);
        GetGeneralRecipeDTO expectedDto = podamFactory.manufacturePojo(GetGeneralRecipeDTO.class);

        try (MockedStatic<GeneralRecipeMapper> mockedMapper = mockStatic(GeneralRecipeMapper.class)) {
            when(generalRecipeRepository.findById(id)).thenReturn(Optional.of(generalRecipe));
            mockedMapper.when(() -> GeneralRecipeMapper.toGetDTO(any(GeneralRecipe.class))).thenReturn(expectedDto);

            GetGeneralRecipeDTO result = generalRecipeService.getGeneralRecipeById(id);

            assertNotNull(result);
            assertEquals(expectedDto, result);
            verify(generalRecipeRepository).findById(id);
        }
    }

    @Test
    void testGetAllGeneralRecipes() {
        List<GeneralRecipe> generalRecipes = easyRandom.objects(GeneralRecipe.class, 5).toList();

        try (MockedStatic<GeneralRecipeMapper> mockedMapper = mockStatic(GeneralRecipeMapper.class)) {
            when(generalRecipeRepository.findAll()).thenReturn(generalRecipes);
            when(GeneralRecipeMapper.toDto(any(GeneralRecipe.class))).thenReturn(podamFactory.manufacturePojo(GeneralRecipeDTO.class));

            List<GeneralRecipeDTO> result = generalRecipeService.getAllGeneralRecipes();

            assertNotNull(result);
            assertEquals(5, result.size());
            verify(generalRecipeRepository).findAll();
        }
    }

    @Test
    void testGetFilteredGeneralRecipes() {
        List<GeneralRecipe> generalRecipes = easyRandom.objects(GeneralRecipe.class, 5).toList();

        try (MockedStatic<GeneralRecipeMapper> mockedMapper = mockStatic(GeneralRecipeMapper.class)) {
            when(generalRecipeRepository.findAll()).thenReturn(generalRecipes);
            when(GeneralRecipeMapper.toGetDTO(any(GeneralRecipe.class))).thenReturn(podamFactory.manufacturePojo(GetGeneralRecipeDTO.class));

            List<GetGeneralRecipeDTO> result = generalRecipeService.getFilteredGeneralRecipes(empty(), empty(), empty(), empty(), empty());

            assertNotNull(result);
            assertEquals(5, result.size());
            verify(generalRecipeRepository).findAll();
        }
    }

    @Test
    void testGetFilteredGeneralRecipesWithFilters() {
        List<GeneralRecipe> generalRecipes = easyRandom.objects(GeneralRecipe.class, 5).toList();
        generalRecipes.stream().forEach(r -> r.setName("Salad"));

        try (MockedStatic<GeneralRecipeMapper> mockedMapper = mockStatic(GeneralRecipeMapper.class)) {
            when(generalRecipeRepository.findByNameRegex(anyString())).thenReturn(generalRecipes);
            when(GeneralRecipeMapper.toGetDTO(any(GeneralRecipe.class))).thenReturn(podamFactory.manufacturePojo(GetGeneralRecipeDTO.class));

            List<GetGeneralRecipeDTO> result = generalRecipeService.getFilteredGeneralRecipes(Optional.of("Salad"), empty(), empty(), empty(), empty());

            assertNotNull(result);
            assertEquals(5, result.size());
            verify(generalRecipeRepository).findByNameRegex("Salad");
        }
    }
}
