package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.recipe.dto.ContainsDTO;
import com.culinary.userservice.recipe.model.Contains;
import com.culinary.userservice.recipe.model.Recipe;

public class ContainsMapper {

    public static Contains toEntity(ContainsDTO containsDTO, Recipe recipe, Ingredient ingredient) {
        Contains contains = new Contains();
        contains.setMeasure(containsDTO.getMeasure());
        contains.setAmount(containsDTO.getAmount());
        contains.setRecipe(recipe);
        contains.setIngredient(ingredient);
        return contains;
    }

    public static ContainsDTO toDto(Contains contains) {
        return ContainsDTO.builder()
                .measure(contains.getMeasure())
                .amount(contains.getAmount())
                .recipeId(contains.getRecipe().getRecipeId())
                .ingredientId(contains.getIngredient().getIngredientId())
                .build();
    }
}