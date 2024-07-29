package com.culinary.userservice.recipe.dto.review;

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
    private long recipeId;
    private Integer rating;
    private String opinion;
}
