package com.culinary.userservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequestDTO {

    private String name;
    private String lastName;
    private String mail;
    private String password;
}
