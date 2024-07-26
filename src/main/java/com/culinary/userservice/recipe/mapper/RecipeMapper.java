package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.RecipeContainsDTO;
import com.culinary.userservice.recipe.dto.RecipeDTO;
import com.culinary.userservice.recipe.dto.RecipeDetailsDTO;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.user.mapper.UserMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static Recipe toEntity(RecipeContainsDTO dto, DietType dietType, GeneralRecipe generalRecipe) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setDietType(dietType);
        recipe.setReviews(new ArrayList<>());
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
                .generalRecipe(GeneralRecipeMapper.toDto(recipe.getGeneralRecipe()))
                .name(recipe.getName())
                .description(recipe.getDescription())
                .dietType(DietTypeMapper.toDto(recipe.getDietType()))
                .contains(recipe.getContains().stream().map(ContainsMapper::toDto).collect(Collectors.toList()))
                .reviews(recipe.getReviews().stream().map(ReviewMapper::toDto).collect(Collectors.toList()))
                .usersWhoFavorited(recipe.getUsersWhoFavorited()
                        .stream()
                        .map(UserMapper::toUserNoDetailsDTO)
                        .collect(Collectors.toSet()))
                .build();
    }
}
