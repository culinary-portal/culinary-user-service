package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.review.PutReviewDTO;
import com.culinary.userservice.recipe.dto.review.ReviewDTO;
import com.culinary.userservice.recipe.mapper.ReviewMapper;
import com.culinary.userservice.recipe.model.Review;
import com.culinary.userservice.recipe.repository.ReviewRepository;
import com.culinary.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final GeneralRecipeService generalRecipeService;

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setOpinion(reviewDTO.getOpinion());
        review.setUser(userService.getUserEntityById(reviewDTO.getUserId()));
        review.setRating(reviewDTO.getRating());
        review.setGeneralRecipe(generalRecipeService.getGeneralRecipeEntityById((int) reviewDTO.getRecipeId()));
        Review savedReview = reviewRepository.save(review);
        return ReviewMapper.toDto(savedReview);
    }

    @Transactional
    public ReviewDTO updateReview(long id, PutReviewDTO dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setOpinion(dto.opinion());
        review.setRating(dto.rating());
        Review updatedReview = reviewRepository.save(review);
        return ReviewMapper.toDto(updatedReview);
    }

    @Transactional
    public void deleteReview(long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public ReviewDTO getReviewById(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return ReviewMapper.toDto(review);
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());
    }
}