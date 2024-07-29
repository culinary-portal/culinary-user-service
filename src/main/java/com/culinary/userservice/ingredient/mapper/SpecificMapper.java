package com.culinary.userservice.ingredient.mapper;

import com.culinary.userservice.ingredient.dto.specific.SpecificDTO;
import com.culinary.userservice.ingredient.dto.specific.SpecificDetailsDTO;
import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.ingredient.model.Specific;
import com.culinary.userservice.user.model.User;

public class SpecificMapper {

    public static Specific toEntity(SpecificDTO specificDto, User user, Ingredient ingredient) {
        Specific specific = new Specific();
        specific.setUser(user);
        specific.setIngredient(ingredient);
        specific.setLikes(specificDto.getLikes());
        return specific;
    }

    public static SpecificDTO toDto(Specific specific) {
        return new SpecificDTO(
                specific.getSpecificId(),
                specific.getUser().getId(),
                specific.getIngredient().getIngredientId(),
                specific.getLikes()
        );
    }

    public static SpecificDetailsDTO toDetailsDto(Specific specific) {
        return SpecificDetailsDTO.builder()
                .specificId(specific.getSpecificId())
                .likes(specific.getLikes())
                .ingredient(IngredientMapper.toDto(specific.getIngredient()))
                .userId(specific.getUser().getId())
                .build();
    }
}