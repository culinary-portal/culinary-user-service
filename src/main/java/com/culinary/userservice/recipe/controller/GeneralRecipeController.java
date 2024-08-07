package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.general.GetGeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.PutGeneralRecipeDTO;
import com.culinary.userservice.recipe.service.GeneralRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/general-recipes")
@RequiredArgsConstructor
public class GeneralRecipeController {

    private final GeneralRecipeService generalRecipeService;

    @PostMapping
    public ResponseEntity<GetGeneralRecipeDTO> createGeneralRecipe(@RequestBody PutGeneralRecipeDTO generalRecipeDTO) {
        GetGeneralRecipeDTO createdGeneralRecipe = generalRecipeService.createGeneralRecipe(generalRecipeDTO);
        return ResponseEntity.ok(createdGeneralRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetGeneralRecipeDTO> updateGeneralRecipe(@PathVariable int id, @RequestBody PutGeneralRecipeDTO generalRecipeDTO) {
        GetGeneralRecipeDTO updatedGeneralRecipe = generalRecipeService.updateGeneralRecipe(id, generalRecipeDTO);
        return ResponseEntity.ok(updatedGeneralRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeneralRecipe(@PathVariable int id) {
        generalRecipeService.deleteGeneralRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetGeneralRecipeDTO> getGeneralRecipeById(@PathVariable int id) {
        GetGeneralRecipeDTO generalRecipeDTO = generalRecipeService.getGeneralRecipeById(id);
        return ResponseEntity.ok(generalRecipeDTO);
    }

    @GetMapping
    public ResponseEntity<List<GetGeneralRecipeDTO>> getGeneralRecipesByFiltered(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> mealType,
            @RequestParam Optional<Integer> maxCalories,
            @RequestParam Optional<String> dietType) {
        List<GetGeneralRecipeDTO> generalRecipes = generalRecipeService.getFilteredGeneralRecipes(name, mealType, maxCalories, dietType);
        return ResponseEntity.ok(generalRecipes);
    }
}