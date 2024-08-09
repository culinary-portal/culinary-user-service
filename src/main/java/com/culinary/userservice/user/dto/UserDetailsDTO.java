package com.culinary.userservice.user.dto;

import com.culinary.userservice.ingredient.dto.specific.GetSpecificDTO;

import java.sql.Date;
import java.util.Set;


public record UserDetailsDTO(
        Long id,
        String email,
        String userName,
        String photoUrl,
        boolean enabled,
        boolean credentialsNonExpired,
        boolean accountNonExpired,
        boolean locked,
        Date birthdate,
        Date createDate,
        Set<GetSpecificDTO> specifics
) {
}
