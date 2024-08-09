package com.culinary.userservice.ingredient.mapper;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import com.culinary.userservice.ingredient.dto.ingredient.PutIngredientDTO;
import com.culinary.userservice.ingredient.model.Ingredient;

public class IngredientMapper {

    public static Ingredient toEntity(PutIngredientDTO dto) {
        return Ingredient.builder()
                .name(dto.name())
                .fat(dto.fat())
                .protein(dto.protein())
                .carbohydrate(dto.carbohydrate())
                .kcal(dto.kcal())
                .isVegan(dto.isVegan())
                .isGlutenFree(dto.isGlutenFree())
                .build();
    }

    public static void updateEntityFromDto(Ingredient entity, PutIngredientDTO dto) {
        entity.setName(dto.name());
        entity.setFat(dto.fat());
        entity.setProtein(dto.protein());
        entity.setCarbohydrate(dto.carbohydrate());
        entity.setKcal(dto.kcal());
        entity.setIsVegan(dto.isVegan());
        entity.setIsGlutenFree(dto.isGlutenFree());
    }

    public static IngredientDTO toDto(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getIngredientId(),
                ingredient.getName(),
                ingredient.getFat(),
                ingredient.getProtein(),
                ingredient.getCarbohydrate(),
                ingredient.getKcal(),
                ingredient.getIsVegan(),
                ingredient.getIsGlutenFree()
        );
    }

}