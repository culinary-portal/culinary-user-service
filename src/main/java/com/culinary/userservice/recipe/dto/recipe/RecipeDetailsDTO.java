package com.culinary.userservice.recipe.dto.recipe;

import com.culinary.userservice.recipe.dto.contains.ContainsDTO;
import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDetailsDTO {
    private int recipeId;
    private GeneralRecipeViewDTO generalRecipe;
    private String name;
    private String description;
    private DietTypeDTO dietType;
    private List<ContainsDTO> contains;
    private Set<UserNoDetailsDTO> userWhoModified = new HashSet<>();
}
