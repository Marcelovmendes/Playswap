package com.example.spotify.common.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
    AUTHENTICATION_EXCEPTION(HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),

    SPOTIFY_API_EXCEPTION(HttpStatus.BAD_GATEWAY),

     GENERAL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND);


    private final HttpStatus httpStatus;

    ExceptionType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
