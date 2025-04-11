package com.example.spotify.common.exception;

public class UserProfileException extends ApplicationException {

    public UserProfileException(String message, ExceptionType type) {
        super(message, type);

    }
    public UserProfileException(String message) {
       super(message, ExceptionType.GENERAL_EXCEPTION);
    }
}
