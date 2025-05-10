package com.example.spotify.common.exception;

import java.time.LocalDateTime;
import java.util.Map;


public record ErrorResponse(
        String code,
        String message,
        String traceId,
        LocalDateTime timestamp,
        Map<String, Object> details
) {}
