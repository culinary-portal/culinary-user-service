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
        entity.setName(dto.getName());
        entity.setMealType(GeneralRecipe.MealType.valueOf(dto.getMealType().toUpperCase().trim()));
        entity.setPhotoUrl(dto.getPhotoUrl());
        entity.setDescription(dto.getDescription());
        entity.setBaseRecipe(baseRecipe);
        entity.setReviews(new ArrayList<>());
        return entity;
    }

    public static GeneralRecipeDTO toDto(GeneralRecipe entity) {
        return GeneralRecipeDTO.builder()
                .generalRecipeId(entity.getGeneralRecipeId())
                .name(entity.getName())
                .mealType(entity.getMealType().name())
                .photoUrl(entity.getPhotoUrl())
                .description(entity.getDescription())
                .baseRecipe(RecipeMapper.toRecipeContainsDTO(entity.getBaseRecipe()))
                .reviews(entity.getReviews().stream().map(ReviewMapper::toDto).collect(Collectors.toList()))
                .rating(countRating(entity.getReviews()))
                .calories(countCalories(entity.getBaseRecipe().getContains()))
                .fat(countFat(entity.getBaseRecipe().getContains()))
                .carbohydrate(countCarbohydrates(entity.getBaseRecipe().getContains()))
                .protein(countProtein(entity.getBaseRecipe().getContains()))
                .recipes(entity.getRecipes().stream()
                        .map(RecipeMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static GetGeneralRecipeDTO toGetDTO(GeneralRecipe entity) {
        return GetGeneralRecipeDTO.builder()
                .generalRecipeId(entity.getGeneralRecipeId())
                .name(entity.getName())
                .mealType(entity.getMealType().name())
                .photoUrl(entity.getPhotoUrl())
                .description(entity.getDescription())
                .baseRecipe(RecipeMapper.toGetRecipeDTO(entity.getBaseRecipe()))
                .reviews(entity.getReviews().stream().map(ReviewMapper::toDto).collect(Collectors.toList()))
                .rating(countRating(entity.getReviews()))
                .calories(countCalories(entity.getBaseRecipe().getContains()))
                .fat(countFat(entity.getBaseRecipe().getContains()))
                .carbohydrate(countCarbohydrates(entity.getBaseRecipe().getContains()))
                .protein(countProtein(entity.getBaseRecipe().getContains()))
                .recipes(entity.getRecipes().stream()
                        .map(RecipeMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static GeneralRecipeViewDTO toGeneralRecipeViewDTO(GeneralRecipe entity) {
        return GeneralRecipeViewDTO.builder()
                .generalRecipeId(entity.getGeneralRecipeId())
                .name(entity.getName())
                .mealType(entity.getMealType().name())
                .photoUrl(entity.getPhotoUrl())
                .description(entity.getDescription())
                .build();
    }
}