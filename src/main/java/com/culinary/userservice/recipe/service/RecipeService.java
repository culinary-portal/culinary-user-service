package com.culinary.userservice.recipe.service;

import com.culinary.userservice.ingredient.service.IngredientService;
import com.culinary.userservice.recipe.dto.recipe.RecipeContainsDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.mapper.ContainsMapper;
import com.culinary.userservice.recipe.mapper.RecipeMapper;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.repository.RecipeRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import com.culinary.userservice.user.repository.UserRepository;
import com.culinary.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final DietTypeService dietTypeService;
    private final GeneralRecipeService genRecipeService;
    private final IngredientService ingredientService;

//    @Transactional
//    public void addFavouriteRecipe(FavoriteDTO favoriteDTO) {
//        User user = userService.getUserEntityById(favoriteDTO.getUserId());
//        if (user == null) {
//            throw new NotFoundException("No logged in user found");
//        }
//        GeneralRecipe recipe = getRecipeEntityById(favoriteDTO.getRecipeId());
//        if (recipe == null) {
//            throw new NotFoundException("Recipe not found");
//        }
//        user.getFavoriteRecipes().add(recipe);
//        userRepository.save(user);
//    }


    @Transactional
    public RecipeDTO createRecipe(RecipeContainsDTO dto) {
        DietType dietType = dietTypeService.getDietTypeEntityByType(dto.getDietType());
        GeneralRecipe generalRecipe = genRecipeService.getGeneralRecipeEntityById(dto.getGeneralRecipeId());
        Recipe recipe = RecipeMapper.toEntity(dto, dietType, generalRecipe);

        Recipe finalRecipe = recipe;
        recipe.setContains(dto.getContains()
                .stream()
                .map(e -> ContainsMapper.toEntity(e, finalRecipe, ingredientService.findEntityById(e.getIngredientId())))
                .collect(toList()));
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.toDto(recipe);
    }

    @Transactional
    public RecipeDTO updateRecipe(int id, RecipeContainsDTO dto) {
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
        return RecipeMapper.toDto(recipe);
    }

    @Transactional
    public void deleteRecipe(int id) {
        recipeRepository.deleteById(id);
    }

    @Transactional
    public RecipeDetailsDTO getRecipeById(int id) {
        return RecipeMapper.toRecipeDetailsDTO(getRecipeEntityById(id));
    }

    @Transactional
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