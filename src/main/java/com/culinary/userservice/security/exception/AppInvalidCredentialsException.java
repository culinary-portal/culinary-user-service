package com.culinary.userservice.security.exception;

public class AppInvalidCredentialsException extends RuntimeException {
    public AppInvalidCredentialsException(String message) {
        super(message);
    }
}
