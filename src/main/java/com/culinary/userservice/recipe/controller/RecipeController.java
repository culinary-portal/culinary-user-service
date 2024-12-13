package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.general.GetGeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.GetRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable long recipeId, @RequestBody PutRecipeDTO recipeContainsDTO) {
        RecipeDTO updatedRecipe = recipeService.updateRecipeDTO(recipeId, recipeContainsDTO);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDetailsDTO> getRecipeById(@PathVariable long recipeId) {
        RecipeDetailsDTO recipe = recipeService.getRecipeById(recipeId);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{userId}/modifications")
    public ResponseEntity<Set<GetRecipeDTO>> getModifications(@PathVariable long userId) {
        Set<GetRecipeDTO> modifications = recipeService.getModifications(userId);
        return ResponseEntity.ok(modifications);
    }

    @GetMapping("/{userId}/modificationsDetails")
    public ResponseEntity<Set<GetGeneralRecipeDTO>> getModificationsDetails(@PathVariable long userId) {
        Set<GetGeneralRecipeDTO> modifications = recipeService.getModificationsDetails(userId);
        return ResponseEntity.ok(modifications);
    }

    @GetMapping("/{userId}/modificationsDetails/{recipeId}")
    public ResponseEntity<Set<GetGeneralRecipeDTO>> getModificationsDetails(@PathVariable long userId, @PathVariable long recipeId) {
        Set<GetGeneralRecipeDTO> modifications = recipeService.getModificationsDetails(userId, recipeId);
        return ResponseEntity.ok(modifications);
    }

    @PostMapping("/{userId}/modifications")
    public ResponseEntity<Set<PutRecipeDTO>> addModification(@PathVariable long userId, @RequestBody PutRecipeDTO recipeContainsDTO) {
        Set<PutRecipeDTO> modifications = recipeService.addModification(userId, recipeContainsDTO);
        return ResponseEntity.ok(modifications);
    }

    @DeleteMapping("/{userId}/modifications/{recipeId}")
    public ResponseEntity<Set<PutRecipeDTO>> deleteModification(@PathVariable long userId, @PathVariable long recipeId) {
        Set<PutRecipeDTO> modifications = recipeService.deleteModification(userId, recipeId);
        return ResponseEntity.ok(modifications);
    }
}