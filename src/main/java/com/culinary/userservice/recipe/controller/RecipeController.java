package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.RecipeContainsDTO;
import com.culinary.userservice.recipe.dto.RecipeDTO;
import com.culinary.userservice.recipe.dto.RecipeDetailsDTO;
import com.culinary.userservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeContainsDTO recipeContainsDTO) {
        RecipeDTO createdRecipe = recipeService.createRecipe(recipeContainsDTO);
        return ResponseEntity.ok(createdRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable int id, @RequestBody RecipeContainsDTO recipeContainsDTO) {
        RecipeDTO updatedRecipe = recipeService.updateRecipe(id, recipeContainsDTO);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailsDTO> getRecipeById(@PathVariable int id) {
        RecipeDetailsDTO recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }
}