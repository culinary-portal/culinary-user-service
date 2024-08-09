package com.culinary.userservice.user.mapper;

import com.culinary.userservice.ingredient.mapper.SpecificMapper;
import com.culinary.userservice.user.dto.PutUserDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.model.User;

import static java.util.stream.Collectors.toSet;

public class UserMapper {

    public static UserNoDetailsDTO toUserNoDetailsDTO(User user) {
        return UserNoDetailsDTO.builder()
                .userId(user.getId())
                .name(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .build();
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return UserDetailsDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .accountNonExpired(user.isAccountNonExpired())
                .enabled(user.isEnabled())
                .createDate(user.getCreateDate())
                .locked(user.isLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .birthdate(user.getBirthdate())
                .userName(user.getUserName())
                .photoUrl(user.getPhotoUrl())
                .specifics(user
                        .getSpecifics()
                        .stream()
                        .map(SpecificMapper::toDetailsDto)
                        .collect(toSet()))
                .build();
    }

    public static User updateUser(User user, PutUserDTO dto) {
        user.setEmail(dto.getEmail());
        user.setPhotoUrl(dto.getPhotoUrl());
        user.setUserName(dto.getUserName());
        user.setBirthdate(dto.getBirthdate());
        return user;
    }
}
