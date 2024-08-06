package com.culinary.userservice.recipe.service;

import com.culinary.userservice.ingredient.service.IngredientService;
import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.mapper.ContainsMapper;
import com.culinary.userservice.recipe.mapper.RecipeMapper;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.repository.GeneralRecipeRepository;
import com.culinary.userservice.recipe.repository.RecipeRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final DietTypeService dietTypeService;
    private final GeneralRecipeRepository generalRecipeRepository;
    private final IngredientService ingredientService;

    private Recipe createRecipeEntity(PutRecipeDTO dto, GeneralRecipe generalRecipe) {
        DietType dietType = dietTypeService.getDietTypeEntityByType(dto.getDietType());
        Recipe recipe = RecipeMapper.toEntity(dto, dietType, generalRecipe);

        Recipe finalRecipe = recipe;
        recipe.setContains(dto.getContains()
                .stream()
                .map(e -> ContainsMapper.toEntity(e, finalRecipe, ingredientService.findEntityById(e.getIngredientId())))
                .collect(toList()));
        return recipeRepository.save(recipe);
    }

    public RecipeDTO createRecipe(PutRecipeDTO dto) {
        GeneralRecipe generalRecipe = generalRecipeRepository.findById(dto.getGeneralRecipeId())
                .orElseThrow(() -> new NotFoundException("GeneralRecipe not found"));

        Recipe recipeEntity = createRecipeEntity(dto, generalRecipe);
        return RecipeMapper.toDto(recipeEntity);
    }

    public Recipe createRecipe(PutRecipeDTO dto, GeneralRecipe generalRecipe) {
        Recipe recipe = createRecipeEntity(dto, generalRecipe);
        return recipe;
    }


    public Recipe updateRecipe(int id, PutRecipeDTO dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setDietType(dietTypeService.getDietTypeEntityByType(dto.getDietType()));
        Recipe finalRecipe = recipe;
        recipe.setContains(dto.getContains()
                .stream()
                .map(e -> ContainsMapper.toEntity(e, finalRecipe, ingredientService.findEntityById(e.getIngredientId())))
                .collect(toList()));
        recipeRepository.save(recipe);
        return recipe;
    }

    public RecipeDTO updateRecipeDTO(int id, PutRecipeDTO dto) {
        return RecipeMapper.toDto(updateRecipe(id, dto));
    }

    @Transactional
    public void deleteRecipe(int recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found with id " + recipeId));

        // Detach associations before deletion
        for (User user : new HashSet<>(recipe.getUsersWhoModified())) {
            user.getModifiedRecipes().remove(recipe);
            userRepository.save(user);
        }

        // Clear the usersWhoModified set in recipe
        recipe.getUsersWhoModified().clear();
        recipeRepository.save(recipe);

        // Now delete the recipe
        recipeRepository.delete(recipe);
    }

    public RecipeDetailsDTO getRecipeById(int id) {
        return RecipeMapper.toRecipeDetailsDTO(getRecipeEntityById(id));
    }

    public Recipe getRecipeEntityById(int id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
    }

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(RecipeMapper::toDto)
                .collect(toList());
    }
}