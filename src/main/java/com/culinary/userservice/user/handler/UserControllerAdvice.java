package com.culinary.userservice.user.handler;


import com.culinary.userservice.security.exception.AppInvalidCredentialsException;
import com.culinary.userservice.user.dto.ResponseDTO;
import com.culinary.userservice.user.exception.AppBusinessException;
import com.culinary.userservice.user.exception.AppGenericException;
import com.culinary.userservice.user.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest req, Exception ex) {
        log.error("Exception intercepted -> {}  caused by -> {}", req.getRequestURL(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(AppGenericException.class)
    public ResponseEntity<Object> handleAppGenericException(HttpServletRequest req, Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(AppBusinessException.class)
    public ResponseEntity<Object> handleAppBusinessException(HttpServletRequest req, Exception ex) {
        log.warn("Exception intercepted -> {}  caused by -> {}", req.getRequestURL(), ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleAppRegisterNotFoundException(HttpServletRequest req, Exception ex) {
        log.error("Exception intercepted: {} caused by {}", req.getRequestURL(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAppAccessDeniedException(HttpServletRequest req, Exception ex) {
        log.warn("Exception intercepted: {} caused by {}", req.getRequestURL(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Object> handleAppAccessAuthenticationCredentialsNotFoundException(HttpServletRequest req, Exception ex) {
        log.warn("Exception intercepted: {} caused by {}", req.getRequestURL(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO("There was an error validating the token"));
    }

    @ExceptionHandler(AppInvalidCredentialsException.class)
    public ResponseEntity<Object> handleAppAccessAppInvalidCredentialsException(HttpServletRequest req, Exception ex) {
        log.warn("Exception intercepted: {} caused by {}", req.getRequestURL(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Invalid username/password!"));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Object> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        String responseBody = "Authentication information is insufficient";
        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }
}
