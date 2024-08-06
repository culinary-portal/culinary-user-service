package com.culinary.userservice.user.controller;

import com.culinary.userservice.ingredient.dto.specific.GetSpecificDTO;
import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserNoDetailsDTO>> checkAuthentication(Authentication authentication) {
        List<UserNoDetailsDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetailsById(@PathVariable @Parameter(description = "ID of the User to get") long id) {
        UserDetailsDTO user = userService.getUserDetails(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/favorite-recipes")
    public ResponseEntity<Set<GeneralRecipeViewDTO>> getFavoriteRecipes(@PathVariable long id) {
        Set<GeneralRecipeViewDTO> favoriteRecipes = userService.getFavoriteRecipes(id);
        return ResponseEntity.ok(favoriteRecipes);
    }

    @GetMapping("/{id}/disliked-ingredients")
    public ResponseEntity<List<GetSpecificDTO>> getDislikedIngredients(@PathVariable long id) {
        List<GetSpecificDTO> dislikedIngredients = userService.getDislikedIngredients(id);
        return ResponseEntity.ok(dislikedIngredients);
    }

    @GetMapping("/{id}/favorite-diets")
    public ResponseEntity<Set<DietTypeDTO>> getFavoriteDiets(@PathVariable long id) {
        Set<DietTypeDTO> favoriteDiets = userService.getFavoriteDiets(id);
        return ResponseEntity.ok(favoriteDiets);
    }

    @GetMapping("/{id}/modifications")
    public ResponseEntity<Set<PutRecipeDTO>> getModifications(@PathVariable long id) {
        Set<PutRecipeDTO> modifications = userService.getModifications(id);
        return ResponseEntity.ok(modifications);
    }

    @GetMapping("/username/{userName}")
    public UserDetailsDTO getUserByUserName(@PathVariable String userName) {
        return userService.getUserByUserName(userName);
    }

    @PostMapping("/{userId}/favorite-recipes/{recipeId}")
    public ResponseEntity<Set<GeneralRecipeViewDTO>> addFavoriteRecipe(@PathVariable long userId, @PathVariable int recipeId) {
        Set<GeneralRecipeViewDTO> favoriteRecipes = userService.addFavoriteRecipe(userId, recipeId);
        return ResponseEntity.ok(favoriteRecipes);
    }
}