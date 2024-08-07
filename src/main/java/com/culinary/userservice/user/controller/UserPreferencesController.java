package com.culinary.userservice.user.controller;


import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserPreferencesController {
    private final UserService userService;

    @GetMapping("/{userId}/favorite-recipes")
    public ResponseEntity<Set<GeneralRecipeViewDTO>> getFavoriteRecipes(@PathVariable long userId) {
        Set<GeneralRecipeViewDTO> favoriteRecipes = userService.getFavoriteRecipes(userId);
        return ResponseEntity.ok(favoriteRecipes);
    }

    @GetMapping("/{userId}/favorite-diets")
    public ResponseEntity<Set<DietTypeDTO>> getFavoriteDiets(@PathVariable long userId) {
        Set<DietTypeDTO> favoriteDiets = userService.getFavoriteDiets(userId);
        return ResponseEntity.ok(favoriteDiets);
    }

    @PostMapping("/{userId}/favorite-recipes/{recipeId}")
    public ResponseEntity<Set<GeneralRecipeViewDTO>> addFavoriteRecipe(@PathVariable long userId, @PathVariable int recipeId) {
        Set<GeneralRecipeViewDTO> favoriteRecipes = userService.addFavoriteRecipe(userId, recipeId);
        return ResponseEntity.ok(favoriteRecipes);
    }

    @PostMapping("/{userId}/favorite-diets/{dietId}")
    public ResponseEntity<Set<DietTypeDTO>> addFavoriteDiet(@PathVariable long userId, @PathVariable int dietId) {
        Set<DietTypeDTO> favoriteDiets = userService.addFavoriteDiet(userId, dietId);
        return ResponseEntity.ok(favoriteDiets);
    }

    @DeleteMapping("/{userId}/favorite-recipes/{recipeId}")
    public ResponseEntity<Set<GeneralRecipeViewDTO>> deleteFavoriteRecipe(@PathVariable long userId, @PathVariable int recipeId) {
        Set<GeneralRecipeViewDTO> favoriteRecipes = userService.deleteFavoriteRecipe(userId, recipeId);
        return ResponseEntity.ok(favoriteRecipes);
    }

    @DeleteMapping("/{userId}/favorite-diets/{dietId}")
    public ResponseEntity<Set<DietTypeDTO>> deleteFavoriteDiet(@PathVariable long userId, @PathVariable int dietId) {
        Set<DietTypeDTO> favoriteDiets = userService.deleteFavoriteDiet(userId, dietId);
        return ResponseEntity.ok(favoriteDiets);
    }
}
