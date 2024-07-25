package com.culinary.userservice.recipe.mapper;


import com.culinary.userservice.recipe.dto.GeneralRecipeDTO;
import com.culinary.userservice.recipe.model.diet.GeneralRecipe;

import java.util.stream.Collectors;

public class GeneralRecipeMapper {

    public static GeneralRecipe toEntity(GeneralRecipeDTO generalRecipeDTO) {
        GeneralRecipe generalRecipe = new GeneralRecipe();
        generalRecipe.setName(generalRecipeDTO.getName());
        generalRecipe.setIsBreakfast(generalRecipeDTO.getIsBreakfast());
        generalRecipe.setIsDinner(generalRecipeDTO.getIsDinner());
        generalRecipe.setIsLunch(generalRecipeDTO.getIsLunch());
        generalRecipe.setIsSupper(generalRecipeDTO.getIsSupper());
        generalRecipe.setRecipes(generalRecipeDTO.getRecipes().stream()
                .map(RecipeMapper::toEntity)
                .collect(Collectors.toList()));
        return generalRecipe;
    }

    public static GeneralRecipeDTO toDto(GeneralRecipe generalRecipe) {
        return GeneralRecipeDTO.builder()
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