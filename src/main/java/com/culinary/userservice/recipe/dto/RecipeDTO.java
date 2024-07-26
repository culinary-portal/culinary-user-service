package com.culinary.userservice.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private int recipeId;
    private String name;
    private String description;
    private String dietType;
    private String photoUrl;
}