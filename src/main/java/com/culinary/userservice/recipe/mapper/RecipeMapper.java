package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.recipe.RecipeContainsDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.user.mapper.UserMapper;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class RecipeMapper {

    public static Recipe toEntity(RecipeContainsDTO dto, DietType dietType, GeneralRecipe generalRecipe) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setDietType(dietType);
        recipe.setGeneralRecipe(generalRecipe);
        return recipe;
    }

    public static RecipeDTO toDto(Recipe recipe) {
        return RecipeDTO.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .dietType(recipe.getDietType().getDietType())
                .build();
    }

    public static RecipeDetailsDTO toRecipeDetailsDTO(Recipe recipe) {
        return RecipeDetailsDTO.builder()
                .recipeId(recipe.getRecipeId())
                .generalRecipe(GeneralRecipeMapper.toGeneralRecipeViewDTO(recipe.getGeneralRecipe()))
                .name(recipe.getName())
                .description(recipe.getDescription())
                .dietType(DietTypeMapper.toDto(recipe.getDietType()))
                .contains(recipe.getContains().stream().map(ContainsMapper::toDto).collect(toList()))
                .userWhoModified(recipe.getUsersWhoModified()
                        .stream()
                        .map(UserMapper::toUserNoDetailsDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static RecipeContainsDTO toRecipeContainsDTO(Recipe recipe) {
        return RecipeContainsDTO.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .dietType(recipe.getDietType().getDietType())
                .contains(recipe.getContains().stream().map(ContainsMapper::toDto).collect(toList()))
                .build();
    }
}
