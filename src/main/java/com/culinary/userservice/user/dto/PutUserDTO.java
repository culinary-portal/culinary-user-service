package com.culinary.userservice.user.dto;

import java.sql.Date;


public record PutUserDTO(String email, String userName, String photoUrl, Date birthdate) {
}
