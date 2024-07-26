package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.ReviewDTO;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.model.Review;
import com.culinary.userservice.user.model.User;


public class ReviewMapper {

    public static Review toEntity(ReviewDTO reviewDTO, User user, Recipe recipe) {
        Review review = new Review();
        review.setUser(user);
        review.setRecipe(recipe);
        review.setRating(reviewDTO.getRating());
        review.setOpinion(reviewDTO.getOpinion());
        return review;
    }

    public static ReviewDTO toDto(Review review) {
        return ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .userId(review.getUser().getId())
                .recipeId(review.getRecipe().getRecipeId())
                .rating(review.getRating())
                .opinion(review.getOpinion())
                .build();
    }
}