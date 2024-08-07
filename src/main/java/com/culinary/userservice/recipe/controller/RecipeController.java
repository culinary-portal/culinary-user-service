package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;


    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody PutRecipeDTO recipeContainsDTO) {
        RecipeDTO createdRecipe = recipeService.createRecipe(recipeContainsDTO);
        return ResponseEntity.ok(createdRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable int id, @RequestBody PutRecipeDTO recipeContainsDTO) {
        RecipeDTO updatedRecipe = recipeService.updateRecipeDTO(id, recipeContainsDTO);
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

    @GetMapping("/{userId}/modifications")
    public ResponseEntity<Set<PutRecipeDTO>> getModifications(@PathVariable long userId) {
        Set<PutRecipeDTO> modifications = recipeService.getModifications(userId);
        return ResponseEntity.ok(modifications);
    }

    @PostMapping("/{userId}/modifications")
    public ResponseEntity<Set<PutRecipeDTO>> addModification(@PathVariable long userId, @RequestBody PutRecipeDTO recipeContainsDTO) {
        Set<PutRecipeDTO> modifications = recipeService.addModification(userId, recipeContainsDTO);
        return ResponseEntity.ok(modifications);
    }

    @DeleteMapping("/{userId}/modifications/{recipeId}")
    public ResponseEntity<Set<PutRecipeDTO>> deleteModification(@PathVariable long userId, @PathVariable int recipeId) {
        Set<PutRecipeDTO> modifications = recipeService.deleteModification(userId, recipeId);
        return ResponseEntity.ok(modifications);
    }
}