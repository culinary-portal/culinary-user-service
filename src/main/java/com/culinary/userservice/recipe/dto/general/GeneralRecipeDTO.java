package com.culinary.userservice.recipe.dto.general;

import com.culinary.userservice.recipe.dto.recipe.PutRecipeDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeDTO;
import com.culinary.userservice.recipe.dto.review.ReviewDTO;
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
    private String mealType;
    private String description;
    private String steps;
    private PutRecipeDTO baseRecipe;
    private List<ReviewDTO> reviews;
    private List<RecipeDTO> recipes;
    private Double rating;
    private Integer calories;
    private Double protein;
    private Double fat;
    private Double carbohydrate;
}
