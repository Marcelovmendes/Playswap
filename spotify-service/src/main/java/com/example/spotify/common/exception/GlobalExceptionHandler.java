package com.example.spotify.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
        log.error("ApplicationException: {}", ex.getMessage(), ex);
        ErrorType errorType = ex.getType();
        HttpStatus status = errorType.getHttpStatus();

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.toString(),
                status.value(),
                request.getContextPath(),
                new Date());
        return new ResponseEntity<>(error, status);
    };
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        log.error("Exception : {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                "An unexpected error occurred",
                "INTERNAL_SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getContextPath(),
                new Date());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.error("AuthenticationException: {}", ex.getMessage(), ex);
        ErrorType errorType = ex.getType();
        HttpStatus status = errorType.getHttpStatus();

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.toString(),
                status.value(),
                request.getContextPath(),
                new Date());

        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(SpotifyApiException.class)
    public ResponseEntity<ErrorResponse> handleSpotifyApiException(SpotifyApiException ex, WebRequest request) {
        log.error("SpotifyApiException: {}", ex.getMessage(), ex);
        ErrorType errorType = ex.getType();
        HttpStatus status = errorType.getHttpStatus();

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.toString(),
                status.value(),
                request.getContextPath(),
                new Date());

        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(UserProfileException.class)
    public ResponseEntity<ErrorResponse> handleUserProfileException(UserProfileException ex, WebRequest request) {
        log.error("UserProfileException: {}", ex.getMessage(), ex);
        ErrorType errorType = ex.getType();
        HttpStatus status = errorType.getHttpStatus();

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.toString(),
                status.value(),
                request.getContextPath(),
                new Date());

        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructureException(InfrastructureException ex, WebRequest request) {
        log.error("InfrastructureException: {}", ex.getMessage(), ex);
        ErrorType errorType = ex.getType();
        HttpStatus status = errorType.getHttpStatus();

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.toString(),
                status.value(),
                request.getContextPath(),
                new Date());

        return new ResponseEntity<>(error, status);
    }

}
