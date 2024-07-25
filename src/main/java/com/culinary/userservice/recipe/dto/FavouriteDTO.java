package com.culinary.userservice.recipe.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteDTO {
    private Long userId;
    private int recipeId;
}
