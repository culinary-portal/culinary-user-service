package com.culinary.userservice.recipe.dto.contains;

public record PutContainsDTO(
        float amount,
        String measure,
        long recipeId,
        long ingredientId
) {
}
