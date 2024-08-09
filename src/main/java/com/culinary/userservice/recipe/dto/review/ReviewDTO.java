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
    private long reviewId;
    private long userId;
    private long recipeId;
    private Integer rating;
    private String opinion;
}
