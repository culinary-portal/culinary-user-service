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
        entity.setName(dto.name());
        entity.setMealType(GeneralRecipe.MealType.valueOf(dto.mealType().toUpperCase().trim()));
        entity.setPhotoUrl(dto.photoUrl());
        entity.setDescription(dto.description());
        entity.setSteps(dto.steps());
        entity.setBaseRecipe(baseRecipe);
        entity.setReviews(new ArrayList<>());
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