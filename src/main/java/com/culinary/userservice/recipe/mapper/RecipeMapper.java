package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.RecipeDTO;
import com.culinary.userservice.recipe.model.Recipe;

public class RecipeMapper {

    public static Recipe toEntity(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setDietType(recipeDTO.getDietType());
        recipe.setProtoUrl(recipeDTO.getPhotoUrl());
        return recipe;
    }

    public static RecipeDTO toDto(Recipe recipe) {
        return RecipeDTO.builder()
                .name(recipe.getName())
                .description(recipe.getDescription())
                .photoUrl(recipe.getProtoUrl())
                .dietType(recipe.getDietType())
                .build();
    }
}
