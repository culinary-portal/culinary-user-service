package com.culinary.userservice.ingredient.dto.ingredient;

public record IngredientDTO(
        long ingredientId,
        String name,
        Double fat,
        Double protein,
        Double carbohydrate,
        Double kcal,
        Boolean isVegan,
        Boolean isGlutenFree
) {
}