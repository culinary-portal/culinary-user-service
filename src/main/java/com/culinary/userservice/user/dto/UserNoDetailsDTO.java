package com.culinary.userservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNoDetailsDTO {
    private long userId;
    private String name;
    private String photoUrl;
}
