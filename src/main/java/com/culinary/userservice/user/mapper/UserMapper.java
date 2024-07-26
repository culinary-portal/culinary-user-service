package com.culinary.userservice.user.mapper;

import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.model.User;

public class UserMapper {

    public static UserNoDetailsDTO toUserNoDetailsDTO(User user) {
        return UserNoDetailsDTO.builder()
                .userId(user.getId())
                .name(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .build();
    }
}
