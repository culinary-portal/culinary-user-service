package com.culinary.userservice.recipe.dto.general;

import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
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
    private long generalRecipeId;
    private String name;
    private String photoUrl;
    private Boolean isBreakfast;
    private Boolean isDinner;
    private Boolean isLunch;
    private Boolean isSupper;
    private String description;
    private List<RecipeDTO> recipes;
}
