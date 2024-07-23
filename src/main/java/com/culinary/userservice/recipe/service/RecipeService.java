package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.diet.Favorite;
import com.culinary.userservice.recipe.dto.RecipeDTO;
import com.culinary.userservice.recipe.mapper.RecipeMapper;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.repository.RecipeRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import com.culinary.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Transactional
    public void assignRecipeToUser(Recipe recipe) {
        User user = userService.getLoggedUser();
        if (user == null) {
            throw new NotFoundException("No logged in user found");
        }

        List<Favorite> favorites = user.getFavorites();
        if (favorites == null) {
            throw new IllegalStateException("Favorites list not initialized");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setRecipe(recipe);

        favorites.add(favorite);
        userRepository.save(user);
    }


    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.toDto(recipe);
    }

    public RecipeDTO updateRecipe(int id, RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());
        // Update other fields as necessary
        recipe = recipeRepository.save(recipe);
        return RecipeMapper.toDto(recipe);
    }

    public void deleteRecipe(int id) {
        recipeRepository.deleteById(id);
    }

    public RecipeDTO getRecipeById(int id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        return RecipeMapper.toDto(recipe);
    }

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }
}