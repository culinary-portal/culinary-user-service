package com.culinary.userservice.ingredient.mapper;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import com.culinary.userservice.ingredient.dto.ingredient.PutIngredientDTO;
import com.culinary.userservice.ingredient.model.Ingredient;

public class IngredientMapper {

    public static Ingredient toEntity(PutIngredientDTO dto) {
        return Ingredient.builder()
                .name(dto.getName())
                .fat(dto.getFat())
                .protein(dto.getProtein())
                .carbohydrate(dto.getCarbohydrate())
                .kcal(dto.getKcal())
                .isVegan(dto.getIsVegan())
                .isGlutenFree(dto.getIsGlutenFree())
                .build();
    }

    public static void updateEntityFromDto(Ingredient entity, PutIngredientDTO dto) {
        entity.setName(dto.getName());
        entity.setFat(dto.getFat());
        entity.setProtein(dto.getProtein());
        entity.setCarbohydrate(dto.getCarbohydrate());
        entity.setKcal(dto.getKcal());
        entity.setIsVegan(dto.getIsVegan());
        entity.setIsGlutenFree(dto.getIsGlutenFree());
    }

    public static IngredientDTO toDto(Ingredient ingredient) {
        return IngredientDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .name(ingredient.getName())
                .fat(ingredient.getFat())
                .protein(ingredient.getProtein())
                .carbohydrate(ingredient.getCarbohydrate())
                .kcal(ingredient.getKcal())
                .isVegan(ingredient.getIsVegan())
                .isGlutenFree(ingredient.getIsGlutenFree())
                .build();
    }

}