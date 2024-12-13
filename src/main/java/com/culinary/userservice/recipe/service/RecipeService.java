package com.culinary.userservice.recipe.service;

import com.culinary.userservice.ingredient.service.IngredientService;
import com.culinary.userservice.recipe.dto.general.GetGeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.GetRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.mapper.ContainsMapper;
import com.culinary.userservice.recipe.mapper.GeneralRecipeMapper;
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
import java.util.Set;
import java.util.stream.Collectors;

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
        DietType dietType = dietTypeService.getDietTypeEntityByType(dto.dietType());
        Recipe recipe = RecipeMapper.toEntity(dto, dietType, generalRecipe);

        Recipe finalRecipe = recipe;
        recipe.setContains(dto.contains()
                .stream()
                .map(e -> ContainsMapper.toEntity(e, finalRecipe, ingredientService.findEntityById(e.ingredientId())))
                .collect(toList()));
        return recipeRepository.save(recipe);
    }

    public RecipeDTO createRecipe(PutRecipeDTO dto) {
        GeneralRecipe generalRecipe = generalRecipeRepository.findById(dto.generalRecipeId())
                .orElseThrow(() -> new NotFoundException("GeneralRecipe not found"));

        Recipe recipeEntity = createRecipeEntity(dto, generalRecipe);
        return RecipeMapper.toDto(recipeEntity);
    }

    public Recipe createRecipe(PutRecipeDTO dto, GeneralRecipe generalRecipe) {
        Recipe recipe = createRecipeEntity(dto, generalRecipe);
        return recipe;
    }


    public Recipe updateRecipe(long id, PutRecipeDTO dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        if (dto.name() != null) recipe.setName(dto.name());
        if (dto.description() != null) recipe.setDescription(dto.description());
        if (dto.dietType() != null) recipe.setDietType(dietTypeService.getDietTypeEntityByType(dto.dietType()));
        if (dto.contains() != null) {
            recipe.setContains(dto.contains().stream()
                    .map(e -> ContainsMapper.toEntity(
                            e, recipe, ingredientService.findEntityById(e.ingredientId())
                    )).collect(toList()));
        }

        recipeRepository.save(recipe);
        return recipe;
    }


    public RecipeDTO updateRecipeDTO(long id, PutRecipeDTO dto) {
        return RecipeMapper.toDto(updateRecipe(id, dto));
    }

    @Transactional
    public void deleteRecipe(long recipeId) {
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

    public RecipeDetailsDTO getRecipeById(long id) {
        return RecipeMapper.toRecipeDetailsDTO(getRecipeEntityById(id));
    }

    public Recipe getRecipeEntityById(long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
    }

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(RecipeMapper::toDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public Set<GetRecipeDTO> getModifications(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return user.getModifiedRecipes().stream()
                .map(RecipeMapper::toGetRecipeDTO)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<GetGeneralRecipeDTO> getModificationsDetails(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Set<Recipe> modifiedRecipes = user.getModifiedRecipes();
        Set<GeneralRecipe> generalRecipes = new HashSet<>();
        for (Recipe recipe : modifiedRecipes) {
            GeneralRecipe generalRecipe = recipe.getGeneralRecipe();
            generalRecipe.setBaseRecipe(recipe);
            generalRecipes.add(generalRecipe);
        }

        return generalRecipes.stream().map(GeneralRecipeMapper::toGetDTO).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<GetGeneralRecipeDTO> getModificationsDetails(long userId, long recipeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Set<Recipe> modifiedRecipes = user.getModifiedRecipes();
        Set<GeneralRecipe> generalRecipes = new HashSet<>();
        for (Recipe recipe : modifiedRecipes) {
            GeneralRecipe generalRecipe = recipe.getGeneralRecipe();
            generalRecipe.setBaseRecipe(recipe);
            generalRecipes.add(generalRecipe);
        }

        return generalRecipes.stream().filter(e -> e.getBaseRecipe().getRecipeId() == recipeId).map(GeneralRecipeMapper::toGetDTO).collect(Collectors.toSet());
    }

    public Set<PutRecipeDTO> addModification(long userId, PutRecipeDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        GeneralRecipe generalRecipe = generalRecipeRepository.findById(dto.generalRecipeId())
                .orElseThrow(() -> new NotFoundException("GeneralRecipe not found"));
        Recipe recipe = createRecipeEntity(dto, generalRecipe);
        user.getModifiedRecipes().add(recipe);
        userRepository.save(user);

        return user.getModifiedRecipes().stream()
                .map(RecipeMapper::toRecipeContainsDTO)
                .collect(Collectors.toSet());
    }

    public Set<PutRecipeDTO> deleteModification(long userId, long recipeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        user.getModifiedRecipes().remove(recipe);
        userRepository.save(user);

        return user.getModifiedRecipes().stream()
                .map(RecipeMapper::toRecipeContainsDTO)
                .collect(Collectors.toSet());
    }
}