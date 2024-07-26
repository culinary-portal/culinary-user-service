package com.culinary.userservice.recipe.dto;

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
    private GeneralRecipeDTO generalRecipe;
    private String name;
    private String description;
    private DietTypeDTO dietType;
    private List<ContainsDTO> contains;
    private List<ReviewDTO> reviews;
    private Set<UserNoDetailsDTO> usersWhoFavorited = new HashSet<>();
}
