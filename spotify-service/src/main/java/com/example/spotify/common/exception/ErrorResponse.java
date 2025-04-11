package com.example.spotify.common.exception;

import java.util.Date;

public record ErrorResponse(
        String message,
        String error,
        int status,
        String path,
        Date timestamp
) {}
