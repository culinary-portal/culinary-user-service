package com.culinary.userservice.recipe.dto.review;


public record ReviewDTO(
        long reviewId,
        long userId,
        long generalRecipeId,
        Integer rating,
        String opinion
) {
}
