package com.example.spotify.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    AUTHENTICATION_EXCEPTION(HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),

    SPOTIFY_API_EXCEPTION(HttpStatus.BAD_GATEWAY),

    GENERAL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR),

    RESOURCE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND);


    private final HttpStatus httpStatus;

    ErrorType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
