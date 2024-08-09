package com.culinary.userservice.recipe.dto.general;


public record GeneralRecipeViewDTO(
        long generalRecipeId,
        String name,
        String photoUrl,
        String mealType,
        String steps,
        String description
) {
}