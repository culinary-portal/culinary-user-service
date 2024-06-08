package com.culinary.userservice.user.exception;

public class AppBusinessException extends RuntimeException {
    public AppBusinessException(String message) {
        super(message);
    }
}
