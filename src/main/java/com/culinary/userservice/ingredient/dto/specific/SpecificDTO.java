package com.culinary.userservice.ingredient.dto.specific;


public record SpecificDTO(
        long specificId,
        long userId,
        long ingredientId,
        Boolean likes
) {
}