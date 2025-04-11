package com.example.spotify.common.exception;

public class AuthenticationException extends ApplicationException {
    public AuthenticationException(String message, ExceptionType type) {
        super(message, type);
    }
    public AuthenticationException(String message) {
        super(message, ExceptionType.AUTHENTICATION_EXCEPTION);
    }

}
