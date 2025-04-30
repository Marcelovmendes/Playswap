package com.example.spotify.common.exception;

import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.text.ParseException;

@Component
public class SpotifyApiExceptionTranslator {

    public RuntimeException translate (Exception e) {

        if (e instanceof IOException) {
            return new InfrastructureException("Error communicating with Spotify API", e, ErrorType.SPOTIFY_API_EXCEPTION);
        } else if (e instanceof SpotifyWebApiException) {
            if (e.getMessage().contains("401")) {
                return new AuthenticationException("Token is invalid or expired", e, ErrorType.TOKEN_EXPIRED);
            }
            return new SpotifyApiException("Spotify API error", e, ErrorType.SPOTIFY_API_EXCEPTION);
        } else if (e instanceof ParseException) {
            return new InfrastructureException("Analysis error while parsing response", e, ErrorType.SPOTIFY_API_EXCEPTION);
        } else if (e instanceof ApplicationException) {
            return (ApplicationException) e;
        }
        return new InfrastructureException("Unexpected error while communicating with Spotify API", e, ErrorType.GENERAL_EXCEPTION);
    }
}
