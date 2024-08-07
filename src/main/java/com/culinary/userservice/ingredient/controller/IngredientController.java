package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import com.culinary.userservice.ingredient.dto.ingredient.PutIngredientDTO;
import com.culinary.userservice.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        List<IngredientDTO> ingredients = ingredientService.findAll();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable int id) {
        IngredientDTO ingredient = ingredientService.findById(id);
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody PutIngredientDTO ingredientDto) {
        IngredientDTO createdIngredient = ingredientService.save(ingredientDto);
        return ResponseEntity.ok(createdIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable int id, @RequestBody PutIngredientDTO ingredientDto) {
        IngredientDTO updatedIngredient = ingredientService.update(id, ingredientDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }
}