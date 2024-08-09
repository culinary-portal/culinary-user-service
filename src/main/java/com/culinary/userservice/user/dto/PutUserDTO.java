package com.culinary.userservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutUserDTO {
    private String email;
    private String userName;
    private String photoUrl;
    private Date birthdate;
}
