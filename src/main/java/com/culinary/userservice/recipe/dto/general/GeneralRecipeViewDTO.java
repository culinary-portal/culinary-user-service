package com.culinary.userservice.recipe.dto.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralRecipeViewDTO {
    private long generalRecipeId;
    private String name;
    private String photoUrl;
    private String mealType;
    private String steps;
    private String description;
}
