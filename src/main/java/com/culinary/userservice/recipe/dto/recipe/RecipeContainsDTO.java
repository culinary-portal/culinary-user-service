package com.culinary.userservice.recipe.dto.recipe;

import com.culinary.userservice.recipe.dto.contains.ContainsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeContainsDTO {
    private int recipeId;
    private String name;
    private String description;
    private String dietType;
    private int generalRecipeId;
    private List<ContainsDTO> contains;
}

