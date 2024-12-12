package com.culinary.userservice.recipe.dto.recipe;

import com.culinary.userservice.recipe.dto.contains.GetContainsDTO;

import java.util.List;


public record GetRecipeDTO(
        long recipeId,
        String name,
        String description,
        String dietType,
        long generalRecipeId,
        List<GetContainsDTO> contains
) {
}