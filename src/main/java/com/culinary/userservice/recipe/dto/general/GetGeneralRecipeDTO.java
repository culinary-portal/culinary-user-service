package com.culinary.userservice.recipe.dto.general;

import com.culinary.userservice.recipe.dto.recipe.GetRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.review.ReviewDTO;

import java.util.List;


public record GetGeneralRecipeDTO(
        long generalRecipeId,
        String name,
        String photoUrl,
        String mealType,
        String description,
        GetRecipeDTO baseRecipe,
        List<ReviewDTO> reviews,
        List<RecipeDTO> recipes,
        String steps,
        Double rating,
        Integer calories,
        Double protein,
        Double fat,
        Double carbohydrate
) {
}