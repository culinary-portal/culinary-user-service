package com.culinary.userservice.recipe.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateReviewDTO(@JsonProperty("rating") Integer rating,
                              @JsonProperty("opinion") String opinion) {
}
