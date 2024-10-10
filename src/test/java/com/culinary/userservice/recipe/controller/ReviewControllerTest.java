package com.culinary.userservice.recipe.controller;

import com.culinary.userservice.recipe.dto.review.ReviewDTO;
import com.culinary.userservice.recipe.service.ReviewService;
import com.culinary.userservice.user.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ActiveProfiles("test")
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private PodamFactory podamFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(reviewController).build();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testGetAllReviews() throws Exception {
        List<ReviewDTO> reviewList = IntStream.range(0, 5)
                .mapToObj(i -> podamFactory.manufacturePojo(ReviewDTO.class))
                .collect(Collectors.toList());
        when(reviewService.getAllReviews()).thenReturn(reviewList);

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reviewId").isNotEmpty())
                .andExpect(jsonPath("$[1].reviewId").isNotEmpty());

        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    public void testGetReviewById() throws Exception {
        ReviewDTO reviewDTO = podamFactory.manufacturePojo(ReviewDTO.class);
        Long reviewId = reviewDTO.reviewId();
        when(reviewService.getReviewById(reviewId)).thenReturn(reviewDTO);

        mockMvc.perform(get("/api/reviews/{id}", reviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(reviewId));

        verify(reviewService, times(1)).getReviewById(reviewId);
    }

    @Test
    public void testGetReviewByIdNotFound() throws Exception {
        Long reviewId = 1L;
        when(reviewService.getReviewById(reviewId)).thenThrow(new NotFoundException("Review not found"));

        mockMvc.perform(get("/api/reviews/{id}", reviewId))
                .andExpect(status().isNotFound());

        verify(reviewService, times(1)).getReviewById(reviewId);
    }

    @Test
    public void testCreateReview() throws Exception {
        ReviewDTO reviewDTO = podamFactory.manufacturePojo(ReviewDTO.class);
        when(reviewService.createReview(any())).thenReturn(reviewDTO);

        mockMvc.perform(post("/api/reviews")
                        .contentType("application/json")
                        .content("{\"opinion\":\"Great recipe!\",\"rating\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").isNotEmpty())
                .andExpect(jsonPath("$.opinion").isNotEmpty());

        verify(reviewService, times(1)).createReview(any());
    }

    @Test
    public void testUpdateReview() throws Exception {
        ReviewDTO reviewDTO = podamFactory.manufacturePojo(ReviewDTO.class);
        Long reviewId = reviewDTO.reviewId();
        when(reviewService.updateReview(eq(reviewId), any())).thenReturn(reviewDTO);

        mockMvc.perform(put("/api/reviews/{id}", reviewId)
                        .contentType("application/json")
                        .content("{\"content\":\"Updated review\",\"rating\":4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(reviewId));

        verify(reviewService, times(1)).updateReview(eq(reviewId), any());
    }

    @Test
    public void testDeleteReview() throws Exception {
        Long reviewId = 1L;
        doNothing().when(reviewService).deleteReview(reviewId);

        mockMvc.perform(delete("/api/reviews/{id}", reviewId))
                .andExpect(status().isNoContent());

        verify(reviewService, times(1)).deleteReview(reviewId);
    }
}
