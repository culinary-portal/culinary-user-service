package com.culinary.userservice.ingredient.mapper;

import com.culinary.userservice.ingredient.dto.IngredientDTO;
import com.culinary.userservice.ingredient.model.Ingredient;

public class IngredientMapper {

    public static Ingredient toEntity(IngredientDTO ingredientDto) {
        return Ingredient.builder()
                .name(ingredientDto.getName())
                .fat(ingredientDto.getFat())
                .protein(ingredientDto.getProtein())
                .carbohydrate(ingredientDto.getCarbohydrate())
                .kcal(ingredientDto.getKcal())
                .isVegan(ingredientDto.getIsVegan())
                .isGlutenFree(ingredientDto.getIsGlutenFree())
                .build();
    }

    public static void updateEntityFromDto(Ingredient ingredient, IngredientDTO ingredientDto) {
        ingredient.setName(ingredientDto.getName());
        ingredient.setFat(ingredientDto.getFat());
        ingredient.setProtein(ingredientDto.getProtein());
        ingredient.setCarbohydrate(ingredientDto.getCarbohydrate());
        ingredient.setKcal(ingredientDto.getKcal());
        ingredient.setIsVegan(ingredientDto.getIsVegan());
        ingredient.setIsGlutenFree(ingredientDto.getIsGlutenFree());
    }

    public static IngredientDTO toDto(Ingredient ingredient) {
        return new IngredientDTO(ingredient.getIngredientId(),
                ingredient.getName(), ingredient.getFat(), ingredient.getProtein(), ingredient.getCarbohydrate(), ingredient.getKcal(), ingredient.getIsVegan(), ingredient.getIsGlutenFree());
    }
}