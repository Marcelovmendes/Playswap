package com.example.spotify.common.exception;

public class SpotifyApiException extends ApplicationException {
    public SpotifyApiException(String message, ExceptionType type) {
        super(message, type);
    }

    public SpotifyApiException(String message) {
        super(message, ExceptionType.SPOTIFY_API_EXCEPTION);
    }
}
