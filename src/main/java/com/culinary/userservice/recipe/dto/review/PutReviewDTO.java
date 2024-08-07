package com.culinary.userservice.recipe.dto.review;


import com.fasterxml.jackson.annotation.JsonProperty;

public record PutReviewDTO(@JsonProperty("rating") Integer rating,
                           @JsonProperty("opinion") String opinion) {
}
