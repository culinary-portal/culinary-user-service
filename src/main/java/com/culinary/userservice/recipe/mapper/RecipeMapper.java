package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.recipe.GetRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDetailsDTO;
import com.culinary.userservice.recipe.model.DietType;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.user.mapper.UserMapper;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class RecipeMapper {

    public static Recipe toEntity(PutRecipeDTO dto, DietType dietType, GeneralRecipe generalRecipe) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setDescription(dto.description());
        recipe.setDietType(dietType);
        recipe.setGeneralRecipe(generalRecipe);
        return recipe;
    }

    public static Recipe toEntity(PutRecipeDTO dto, DietType dietType) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setDescription(dto.description());
        recipe.setDietType(dietType);
        recipe.setGeneralRecipe(null);
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

    public static PutRecipeDTO toRecipeContainsDTO(Recipe recipe) {
        return new PutRecipeDTO(
                recipe.getName(),
                recipe.getDescription(),
                recipe.getDietType().getDietType(),
                recipe.getGeneralRecipe().getGeneralRecipeId(),
                recipe.getContains().stream().map(ContainsMapper::toDto).collect(toList())
        );
    }

    public static GetRecipeDTO toGetRecipeDTO(Recipe dto) {
        return new GetRecipeDTO(
                dto.getName(),
                dto.getDescription(),
                dto.getDietType().getDietType(),
                dto.getGeneralRecipe().getGeneralRecipeId(),
                dto.getContains().stream().map(ContainsMapper::toGetDTO).collect(Collectors.toList())
        );
    }
}
