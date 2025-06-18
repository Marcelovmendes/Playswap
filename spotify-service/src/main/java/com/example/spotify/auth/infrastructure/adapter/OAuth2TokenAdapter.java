package com.example.spotify.auth.infrastructure.adapter;

import com.example.spotify.auth.domain.entity.Token;
import com.example.spotify.auth.domain.service.OAuth2TokenPort;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OAuth2TokenAdapter implements OAuth2TokenPort {


    @Override
    public Token createFromSession(String accessToken, String refreshToken, Long expiryMillis) {
        if (accessToken == null || refreshToken == null || expiryMillis == null) {
            return null;
        }
        long expiresInSeconds = (expiryMillis - Instant.now().toEpochMilli()) / 1000;
        if (expiresInSeconds <= 0) return null;

        return Token.create(accessToken, refreshToken, (int)expiresInSeconds);
    }
    @Override
    public Token createFromOAuth2Token(Token originalToken) {
        // Se precisar converter de OAuth2Token para UserToken
        return originalToken;
    }
}