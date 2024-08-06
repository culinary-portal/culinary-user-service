package com.culinary.userservice.ingredient.mapper;

import com.culinary.userservice.ingredient.dto.specific.GetSpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.PutSpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.SpecificDTO;
import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.ingredient.model.Specific;
import com.culinary.userservice.user.model.User;

public class SpecificMapper {

    public static Specific toEntity(PutSpecificDTO dto, User user, Ingredient ingredient) {
        Specific specific = new Specific();
        specific.setUser(user);
        specific.setIngredient(ingredient);
        specific.setLikes(dto.getLikes());
        return specific;
    }

    public static SpecificDTO toDto(Specific entity) {
        return new SpecificDTO(
                entity.getSpecificId(),
                entity.getUser().getId(),
                entity.getIngredient().getIngredientId(),
                entity.getLikes()
        );
    }

    public static GetSpecificDTO toDetailsDto(Specific entity) {
        return GetSpecificDTO.builder()
                .specificId(entity.getSpecificId())
                .likes(entity.getLikes())
                .ingredient(IngredientMapper.toDto(entity.getIngredient()))
                .userId(entity.getUser().getId())
                .build();
    }
}