package com.culinary.userservice.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterDTO(@JsonProperty("username") String username,
                          @JsonProperty("email") String email,
                          @JsonProperty("password") String password) {
}