package com.culinary.userservice.recipe.dto.contains;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetContainsDTO {
    private float amount;
    private String measure;
    private int recipeId;
    private IngredientDTO ingredient;
}
