package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.review.PutReviewDTO;
import com.culinary.userservice.recipe.dto.review.ReviewDTO;
import com.culinary.userservice.recipe.model.Review;
import com.culinary.userservice.recipe.repository.ReviewRepository;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.service.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private GeneralRecipeService generalRecipeService;

    @InjectMocks
    private ReviewService reviewService;

    private EasyRandom generator = new EasyRandom();

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = generator.nextObject(User.class);
    }

    @Test
    void testCreateReview() {
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 1L, 5, "Great!");
        Review review = generator.nextObject(Review.class);
        review.setOpinion("Great!");
        review.setRating(5);
        review.setUser(user);

        when(userService.getUserEntityById(1L)).thenReturn(null);
        when(generalRecipeService.getGeneralRecipeEntityById(1)).thenReturn(null);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertEquals("Great!", result.opinion());
        assertEquals(5, result.rating());
    }

    @Test
    void testUpdateReview() {
        PutReviewDTO putReviewDTO = new PutReviewDTO(4, "Updated opinion");
        Review review = generator.nextObject(Review.class);
        review.setOpinion("Original opinion");
        review.setRating(3);
        review.setUser(user);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDTO result = reviewService.updateReview(1L, putReviewDTO);

        assertEquals("Updated opinion", result.opinion());
        assertEquals(4, result.rating());
    }

    @Test
    void testDeleteReview() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReview(1L);

        verify(reviewRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetReviewById() {
        Review review = generator.nextObject(Review.class);
        review.setOpinion("Great!");
        review.setRating(5);
        review.setUser(user);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        ReviewDTO result = reviewService.getReviewById(1L);

        assertEquals("Great!", result.opinion());
        assertEquals(5, result.rating());
    }

    @Test
    void testGetAllReviews() {
        Review review = generator.nextObject(Review.class);
        review.setOpinion("Great!");
        review.setRating(5);
        review.setUser(user);

        when(userService.getUserEntityById(1L)).thenReturn(user);
        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(review));

        List<ReviewDTO> result = reviewService.getAllReviews();

        assertEquals(1, result.size());
        assertEquals("Great!", result.get(0).opinion());
        assertEquals(5, result.get(0).rating());
    }
}