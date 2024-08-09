package com.culinary.userservice.recipe.dto.contains;

public record ContainsDTO(
        float amount,
        String measure,
        long recipeId,
        long ingredientId
) {
}