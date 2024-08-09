package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.review.ReviewDTO;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Review;
import com.culinary.userservice.user.model.User;


public class ReviewMapper {

    public static Review toEntity(ReviewDTO reviewDTO, User user, GeneralRecipe recipe) {
        Review review = new Review();
        review.setUser(user);
        review.setGeneralRecipe(recipe);
        review.setRating(reviewDTO.rating());
        review.setOpinion(reviewDTO.opinion());
        return review;
    }

    public static ReviewDTO toDto(Review review) {
        return new  ReviewDTO(
                review.getReviewId(),
                review.getUser().getId(),
                review.getGeneralRecipe().getGeneralRecipeId(),
                review.getRating(),
                review.getOpinion());
    }
}