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
        if (entity == null || dto == null) throw new IllegalArgumentException("Entity and DTO must not be null");
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.fat() != null) entity.setFat(dto.fat());
        if (dto.protein() != null) entity.setProtein(dto.protein());
        if (dto.carbohydrate() != null) entity.setCarbohydrate(dto.carbohydrate());
        if (dto.kcal() != null) entity.setKcal(dto.kcal());
        if (dto.isVegan() != null) entity.setIsVegan(dto.isVegan());
        if (dto.isGlutenFree() != null) entity.setIsGlutenFree(dto.isGlutenFree());
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