package com.culinary.userservice.ingredient.dto.specific;

public record PutSpecificDTO(
        long userId,
        long ingredientID,
        Boolean likes
) {
}
