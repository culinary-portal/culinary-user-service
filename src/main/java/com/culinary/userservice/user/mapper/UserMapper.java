package com.culinary.userservice.user.mapper;

import com.culinary.userservice.ingredient.mapper.SpecificMapper;
import com.culinary.userservice.user.dto.PutUserDTO;
import com.culinary.userservice.user.dto.UserDetailsDTO;
import com.culinary.userservice.user.dto.UserNoDetailsDTO;
import com.culinary.userservice.user.model.User;

import static java.util.stream.Collectors.toSet;

public class UserMapper {

    public static UserNoDetailsDTO toUserNoDetailsDTO(User user) {
        return new UserNoDetailsDTO(
                user.getId(),
                user.getUserName(),
                user.getPhotoUrl()
        );
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(
                user.getId(),
                user.getEmail(),
                user.getUserName(),
                user.getPhotoUrl(),
                user.isEnabled(),
                user.isCredentialsNonExpired(),
                user.isAccountNonExpired(),
                user.isLocked(),
                user.getBirthdate(),
                user.getCreateDate(),
                user.getSpecifics()
                        .stream()
                        .map(SpecificMapper::toDetailsDto)
                        .collect(toSet())
        );
    }

    public static User updateUser(User user, PutUserDTO dto) {
        user.setEmail(dto.email());
        user.setPhotoUrl(dto.photoUrl());
        user.setUserName(dto.userName());
        user.setBirthdate(dto.birthdate());
        return user;
    }
}
