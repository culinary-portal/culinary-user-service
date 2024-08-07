package com.culinary.userservice.recipe.dto.general;

import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutGeneralRecipeDTO {
    private String name;
    private String photoUrl;
    private String mealType;
    private String description;
    private String steps;
    private PutRecipeDTO baseRecipe;
}
