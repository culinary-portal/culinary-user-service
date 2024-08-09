package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.ingredient.mapper.IngredientMapper;
import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.recipe.dto.contains.ContainsDTO;
import com.culinary.userservice.recipe.dto.contains.GetContainsDTO;
import com.culinary.userservice.recipe.model.Contains;
import com.culinary.userservice.recipe.model.Recipe;

public class ContainsMapper {

    public static Contains toEntity(ContainsDTO containsDTO, Recipe recipe, Ingredient ingredient) {
        Contains contains = new Contains();
        contains.setMeasure(containsDTO.measure());
        contains.setAmount(containsDTO.amount());
        contains.setRecipe(recipe);
        contains.setIngredient(ingredient);
        return contains;
    }

    public static ContainsDTO toDto(Contains contains) {
        return new ContainsDTO(
                contains.getAmount(),
                contains.getMeasure(),
                contains.getRecipe().getRecipeId(),
                contains.getIngredient().getIngredientId());
    }

    public static GetContainsDTO toGetDTO(Contains entity) {
        return new GetContainsDTO(
                entity.getAmount(),
                entity.getMeasure(),
                entity.getRecipe().getRecipeId(),
                IngredientMapper.toDto(entity.getIngredient()));
    }
}