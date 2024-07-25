package com.culinary.userservice.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralRecipeDTO {
    private String name;
    private Boolean isBreakfast;
    private Boolean isDinner;
    private Boolean isLunch;
    private Boolean isSupper;
    private List<RecipeDTO> recipes;
}
