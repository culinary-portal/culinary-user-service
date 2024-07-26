package com.culinary.userservice.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private int reviewId;
    private Long userId;
    private int recipeId;
    private Integer rating;
    private String opinion;
}
