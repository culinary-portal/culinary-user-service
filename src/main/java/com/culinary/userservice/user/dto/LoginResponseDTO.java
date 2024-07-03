package com.culinary.userservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginResponseDTO {

    private final String tokenType = "Bearer";
    private String accessToken;
    private UUID id;
    private String name;
    private String email;

}
