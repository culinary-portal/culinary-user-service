package com.culinary.userservice.ingredient.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutIngredientDTO {
    private String name;
    private Double fat;
    private Double protein;
    private Double carbohydrate;
    private Double kcal;
    private Boolean isVegan;
    private Boolean isGlutenFree;
}