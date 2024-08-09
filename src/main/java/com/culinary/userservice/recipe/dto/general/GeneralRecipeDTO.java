package com.culinary.userservice.recipe.dto.general;

import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.review.ReviewDTO;

import java.util.List;

public record GeneralRecipeDTO(
        long generalRecipeId,
        String name,
        String photoUrl,
        String mealType,
        String description,
        String steps,
        PutRecipeDTO baseRecipe,
        List<ReviewDTO> reviews,
        List<RecipeDTO> recipes,
        Double rating,
        Integer calories,
        Double protein,
        Double fat,
        Double carbohydrate
) {
}