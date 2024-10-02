package com.culinary.userservice.recipe.service;

import com.culinary.userservice.ingredient.service.IngredientService;
import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.mapper.RecipeMapper;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.repository.GeneralRecipeRepository;
import com.culinary.userservice.recipe.repository.RecipeRepository;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceTest {
    private final PodamFactory factory = new PodamFactoryImpl();
    private final EasyRandom generator = new EasyRandom();
    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private DietTypeService dietTypeService;

    @Mock
    private GeneralRecipeRepository generalRecipeRepository;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRecipe() {
        PutRecipeDTO dto = factory.manufacturePojo(PutRecipeDTO.class);
        GeneralRecipe generalRecipe = new GeneralRecipe();
        Recipe recipe = generator.nextObject(Recipe.class);
        when(generalRecipeRepository.findById(dto.generalRecipeId())).thenReturn(Optional.of(generalRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        RecipeDTO result = recipeService.createRecipe(dto);

        assertEquals(RecipeMapper.toDto(recipe), result);
    }

    @Test
    void testUpdateRecipe() {
        PutRecipeDTO dto = factory.manufacturePojo(PutRecipeDTO.class);
        Recipe recipe = generator.nextObject(Recipe.class);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(dietTypeService.getDietTypeEntityByType(any())).thenReturn(generator.nextObject(DietType.class));

        RecipeDTO result = recipeService.updateRecipeDTO(1L, dto);

        assertEquals(RecipeMapper.toDto(recipe), result);
    }

    @Test
    void testDeleteRecipe() {
        Recipe recipe = generator.nextObject(Recipe.class);
        recipe.setUsersWhoModified(new HashSet<>());
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        doNothing().when(recipeRepository).delete(recipe);

        recipeService.deleteRecipe(1L);

        verify(recipeRepository, times(1)).delete(recipe);
    }

    @Test
    void testGetRecipeById() {
        Recipe recipe = generator.nextObject(Recipe.class);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        RecipeDetailsDTO result = recipeService.getRecipeById(1L);

        assertEquals(RecipeMapper.toRecipeDetailsDTO(recipe), result);
    }

    @Test
    void testGetAllRecipes() {
        Recipe recipe = generator.nextObject(Recipe.class);
        when(recipeRepository.findAll()).thenReturn(Collections.singletonList(recipe));

        List<RecipeDTO> result = recipeService.getAllRecipes();

        assertEquals(1, result.size());
        assertEquals(RecipeMapper.toDto(recipe), result.get(0));
    }

    @Test
    void testGetModifications() {
        User user = new User();
        user.setModifiedRecipes(new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<PutRecipeDTO> result = recipeService.getModifications(1L);

        assertEquals(0, result.size());
    }

    @Test
    void testAddModification() {
        User user = generator.nextObject(User.class);
        GeneralRecipe generalRecipe = new GeneralRecipe();
        PutRecipeDTO dto = factory.manufacturePojo(PutRecipeDTO.class);
        Recipe recipe = generator.nextObject(Recipe.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(generalRecipeRepository.findById(dto.generalRecipeId())).thenReturn(Optional.of(generalRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Set<PutRecipeDTO> result = recipeService.addModification(1L, dto);

        assertEquals(1, result.size());
    }

    @Test
    void testDeleteModification() {
        User user = new User();
        Recipe recipe = generator.nextObject(Recipe.class);
        user.getModifiedRecipes().add(recipe);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Set<PutRecipeDTO> result = recipeService.deleteModification(1L, 1L);

        assertEquals(0, result.size());
    }
}