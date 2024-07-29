package com.culinary.userservice.recipe.dto.contains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContainsDTO {
    private float amount;
    private String measure;
    private int recipeId;
    private int ingredientId;
}
