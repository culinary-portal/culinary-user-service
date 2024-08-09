package com.culinary.userservice.ingredient.dto.substitute;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;

public record GetSubstituteDTO(
        long substituteId,
        IngredientDTO ingredient1,
        IngredientDTO ingredient2,
        Float proportionI1I2
) {
}
