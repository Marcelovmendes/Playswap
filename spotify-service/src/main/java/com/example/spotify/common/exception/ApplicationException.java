package com.example.spotify.common.exception;

public class ApplicationException extends RuntimeException {
    private final ExceptionType type;

    public ApplicationException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
    public ApplicationException(String message, Throwable cause, ExceptionType type) {
        super(message, cause);
        this.type = type;
    }
    public ExceptionType getType() {
        return type;
    }
}
