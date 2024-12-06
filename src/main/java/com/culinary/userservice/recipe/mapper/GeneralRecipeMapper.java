package com.culinary.userservice.recipe.mapper;


import com.culinary.userservice.recipe.dto.general.GeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.general.GetGeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.PutGeneralRecipeDTO;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.culinary.userservice.recipe.util.RecipeUtils.*;

public class GeneralRecipeMapper {

    public static GeneralRecipe updateEntity(GeneralRecipe entity, PutGeneralRecipeDTO dto, Recipe baseRecipe) {
        if (entity == null || dto == null) throw new IllegalArgumentException("Entity and DTO must not be null");
        entity.setName(dto.name() != null ? dto.name() : entity.getName());
        entity.setMealType(dto.mealType() != null ?
                GeneralRecipe.MealType.valueOf(dto.mealType().toUpperCase().trim()) : entity.getMealType());
        entity.setPhotoUrl(dto.photoUrl() != null ? dto.photoUrl() : entity.getPhotoUrl());
        entity.setDescription(dto.description() != null ? dto.description() : entity.getDescription());
        entity.setSteps(dto.steps() != null ? dto.steps() : entity.getSteps());
        entity.setBaseRecipe(baseRecipe != null ? baseRecipe : entity.getBaseRecipe());
        entity.setReviews(entity.getReviews() != null ? entity.getReviews() : new ArrayList<>());

        return entity;
    }

    public static GeneralRecipeDTO toDto(GeneralRecipe entity) {
        return new GeneralRecipeDTO(
                entity.getGeneralRecipeId(),
                entity.getName(),
                entity.getPhotoUrl(),
                entity.getMealType().name(),
                entity.getDescription(),
                entity.getSteps(),
                RecipeMapper.toRecipeContainsDTO(entity.getBaseRecipe()),
                entity.getReviews().stream()
                        .map(ReviewMapper::toDto)
                        .collect(Collectors.toList()),
                entity.getRecipes().stream()
                        .map(RecipeMapper::toDto)
                        .collect(Collectors.toList()),
                countRating(entity.getReviews()),
                countCalories(entity.getBaseRecipe().getContains()),
                countProtein(entity.getBaseRecipe().getContains()),
                countFat(entity.getBaseRecipe().getContains()),
                countCarbohydrates(entity.getBaseRecipe().getContains())
        );
    }


    public static GetGeneralRecipeDTO toGetDTO(GeneralRecipe entity) {
        return new GetGeneralRecipeDTO(
                entity.getGeneralRecipeId(),
                entity.getName(),
                entity.getPhotoUrl(),
                entity.getMealType().name(),
                entity.getDescription(),
                RecipeMapper.toGetRecipeDTO(entity.getBaseRecipe()),
                entity.getReviews().stream()
                        .map(ReviewMapper::toDto)
                        .collect(Collectors.toList()),
                entity.getRecipes().stream()
                        .map(RecipeMapper::toDto)
                        .collect(Collectors.toList()),
                entity.getSteps(),
                countRating(entity.getReviews()),
                countCalories(entity.getBaseRecipe().getContains()),
                countProtein(entity.getBaseRecipe().getContains()),
                countFat(entity.getBaseRecipe().getContains()),
                countCarbohydrates(entity.getBaseRecipe().getContains())
        );
    }

    public static GeneralRecipeViewDTO toGeneralRecipeViewDTO(GeneralRecipe entity) {
        return new GeneralRecipeViewDTO(
                entity.getGeneralRecipeId(),
                entity.getName(),
                entity.getPhotoUrl(),
                entity.getMealType().name(),
                entity.getSteps(),
                entity.getDescription());
    }
}