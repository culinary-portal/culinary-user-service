package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.GeneralRecipeDTO;
import com.culinary.userservice.recipe.service.GeneralRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general-recipes")
@RequiredArgsConstructor
public class GeneralRecipeController {

    private final GeneralRecipeService generalRecipeService;

    @PostMapping
    public ResponseEntity<GeneralRecipeDTO> createGeneralRecipe(@RequestBody GeneralRecipeDTO generalRecipeDTO) {
        GeneralRecipeDTO createdGeneralRecipe = generalRecipeService.createGeneralRecipe(generalRecipeDTO);
        return ResponseEntity.ok(createdGeneralRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralRecipeDTO> updateGeneralRecipe(@PathVariable int id, @RequestBody GeneralRecipeDTO generalRecipeDTO) {
        GeneralRecipeDTO updatedGeneralRecipe = generalRecipeService.updateGeneralRecipe(id, generalRecipeDTO);
        return ResponseEntity.ok(updatedGeneralRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeneralRecipe(@PathVariable int id) {
        generalRecipeService.deleteGeneralRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralRecipeDTO> getGeneralRecipeById(@PathVariable int id) {
        GeneralRecipeDTO generalRecipeDTO = generalRecipeService.getGeneralRecipeById(id);
        return ResponseEntity.ok(generalRecipeDTO);
    }

    @GetMapping
    public ResponseEntity<List<GeneralRecipeDTO>> getAllGeneralRecipes() {
        List<GeneralRecipeDTO> generalRecipes = generalRecipeService.getAllGeneralRecipes();
        return ResponseEntity.ok(generalRecipes);
    }
}