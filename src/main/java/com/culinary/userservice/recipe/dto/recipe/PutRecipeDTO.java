package com.culinary.userservice.recipe.dto.recipe;

import com.culinary.userservice.recipe.dto.contains.ContainsDTO;

import java.util.List;


public record PutRecipeDTO(
        String name,
        String description,
        String dietType,
        long generalRecipeId,
        List<ContainsDTO> contains
) {
}