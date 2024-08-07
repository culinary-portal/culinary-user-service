package com.culinary.userservice.user.dto;

import com.culinary.userservice.ingredient.dto.specific.GetSpecificDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDTO {
    private Long id;
    private String email;
    private String userName;
    private String photoUrl;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean locked;
    private Date birthdate;
    private Date createDate;
    private Set<GetSpecificDTO> specifics;
}
