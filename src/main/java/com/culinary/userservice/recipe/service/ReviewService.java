package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.ReviewDTO;
import com.culinary.userservice.recipe.dto.UpdateReviewDTO;
import com.culinary.userservice.recipe.mapper.ReviewMapper;
import com.culinary.userservice.recipe.model.Review;
import com.culinary.userservice.recipe.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = new Review();
//        review.setTitle(reviewDTO.getTitle());
//        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        // Set other fields as necessary

        Review savedReview = reviewRepository.save(review);
        return ReviewMapper.toDto(savedReview);
    }

    @Transactional
    public ReviewDTO updateReview(int id, UpdateReviewDTO dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setOpinion(dto.opinion());
        review.setRating(dto.rating());
        Review updatedReview = reviewRepository.save(review);
        return ReviewMapper.toDto(updatedReview);
    }

    @Transactional
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public ReviewDTO getReviewById(int id) {
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