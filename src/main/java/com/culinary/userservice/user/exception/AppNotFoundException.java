package com.culinary.userservice.user.exception;

public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(String message) {
        super(message);
    }
}
