package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.recipe.dto.ContainsDTO;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.model.diet.Contains;

public class ContainsMapper {

    public static Contains toEntity(ContainsDTO containsDTO, Recipe recipe, Ingredient ingredient) {
        Contains contains = new Contains();
        contains.setName(containsDTO.getName());
        contains.setRecipe(recipe);
        contains.setIngredient(ingredient);
        return contains;
    }

    public static ContainsDTO toDto(Contains contains) {
        return ContainsDTO.builder()
                .name(contains.getName())
                .recipeId(contains.getRecipe().getRecipeId())
                .ingredientId(contains.getIngredient().getIngredientId())
                .build();
    }
}