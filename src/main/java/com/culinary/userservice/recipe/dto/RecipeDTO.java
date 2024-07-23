package com.culinary.userservice.recipe.dto;

import com.culinary.userservice.recipe.diet.Contains;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private int id;
    private String name;
    private String description;
    private String dietType;
    private List<Contains> contains;
}