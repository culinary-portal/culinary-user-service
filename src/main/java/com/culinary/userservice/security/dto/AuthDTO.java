package com.culinary.userservice.security.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthDTO(@JsonProperty("email") String email,
                      @JsonProperty("password") String password) {
}
