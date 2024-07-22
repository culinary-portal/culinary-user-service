package com.culinary.userservice.ingredient.controller;

import com.culinary.userservice.ingredient.dto.IngredientDTO;
import com.culinary.userservice.ingredient.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @Operation(summary = "Get all ingredients", responses = {
            @ApiResponse(responseCode = "200", description = "Found ingredients",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        List<IngredientDTO> ingredients = ingredientService.findAll();
        return ResponseEntity.ok(ingredients);
    }

    @Operation(summary = "Get an ingredient by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Found the ingredient",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable int id) {
        IngredientDTO ingredient = ingredientService.findById(id);
        return ResponseEntity.ok(ingredient);
    }

    @Operation(summary = "Create a new ingredient", responses = {
            @ApiResponse(responseCode = "200", description = "Ingredient created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientDTO.class)))
    })
    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDto) {
        IngredientDTO createdIngredient = ingredientService.save(ingredientDto);
        return ResponseEntity.ok(createdIngredient);
    }

    @Operation(summary = "Update an existing ingredient", responses = {
            @ApiResponse(responseCode = "200", description = "Ingredient updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable int id, @RequestBody IngredientDTO ingredientDto) {
        IngredientDTO updatedIngredient = ingredientService.update(id, ingredientDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    @Operation(summary = "Delete an ingredient by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Ingredient deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }
}