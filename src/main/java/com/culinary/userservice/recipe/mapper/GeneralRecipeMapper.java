package com.culinary.userservice.recipe.mapper;


import com.culinary.userservice.recipe.dto.GeneralRecipeDTO;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralRecipeMapper {

    public static GeneralRecipe toEntity(GeneralRecipeDTO generalRecipeDTO, List<Recipe> recipes) {
        GeneralRecipe generalRecipe = new GeneralRecipe();
        generalRecipe.setName(generalRecipeDTO.getName());
        generalRecipe.setIsBreakfast(generalRecipeDTO.getIsBreakfast());
        generalRecipe.setIsDinner(generalRecipeDTO.getIsDinner());
        generalRecipe.setIsLunch(generalRecipeDTO.getIsLunch());
        generalRecipe.setIsSupper(generalRecipeDTO.getIsSupper());
        generalRecipe.setRecipes(recipes);
        return generalRecipe;
    }

    public static GeneralRecipeDTO toDto(GeneralRecipe generalRecipe) {
        return GeneralRecipeDTO.builder()
                .generalRecipeId(generalRecipe.getGeneralRecipeId())
                .name(generalRecipe.getName())
                .isBreakfast(generalRecipe.getIsBreakfast())
                .isDinner(generalRecipe.getIsDinner())
                .isLunch(generalRecipe.getIsLunch())
                .isSupper(generalRecipe.getIsSupper())
                .recipes(generalRecipe.getRecipes().stream()
                        .map(RecipeMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}