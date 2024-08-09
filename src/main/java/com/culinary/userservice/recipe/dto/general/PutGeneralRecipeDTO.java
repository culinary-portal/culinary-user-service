package com.culinary.userservice.recipe.dto.general;

import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;

public record PutGeneralRecipeDTO(
        String name,
        String photoUrl,
        String mealType,
        String description,
        String steps,
        PutRecipeDTO baseRecipe
) {
}
