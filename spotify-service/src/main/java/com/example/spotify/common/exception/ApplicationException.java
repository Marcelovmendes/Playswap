package com.example.spotify.common.exception;

public class ApplicationException extends RuntimeException {
    private final ErrorType type;

    public ApplicationException(String message, ErrorType type) {
        super(message);
        this.type = type;
    }
    public ApplicationException(String message, Throwable cause, ErrorType type) {
        super(message, cause);
        this.type = type;
    }
    public ErrorType getType() {
        return type;
    }
}
