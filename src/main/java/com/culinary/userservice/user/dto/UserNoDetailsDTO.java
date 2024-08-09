package com.culinary.userservice.user.dto;

public record UserNoDetailsDTO(
        long userId,
        String name,
        String photoUrl
) {
}

