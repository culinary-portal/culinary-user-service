package com.culinary.userservice.recipe.dto.contains;

public record ContainsDTO(
        long containsId,
        float amount,
        String measure,
        long recipeId,
        long ingredientId
) {
}