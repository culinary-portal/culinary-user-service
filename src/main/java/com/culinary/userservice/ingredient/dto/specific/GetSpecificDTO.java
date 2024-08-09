package com.culinary.userservice.ingredient.dto.specific;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;

public record GetSpecificDTO(
        long specificId,
        long userId,
        IngredientDTO ingredient,
        Boolean likes
) {
}
