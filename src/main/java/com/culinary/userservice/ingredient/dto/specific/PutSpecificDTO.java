package com.culinary.userservice.ingredient.dto.specific;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;


public record PutSpecificDTO(
        long userId,
        long ingredientID,
        Boolean likes
) {
}
