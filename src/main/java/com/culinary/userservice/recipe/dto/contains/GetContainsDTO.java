package com.culinary.userservice.recipe.dto.contains;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;


public record GetContainsDTO(
        float amount,
        String measure,
        long recipeId,
        IngredientDTO ingredient
) {
}