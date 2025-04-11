package com.example.spotify.auth.infrastructure.adapter;

import com.example.spotify.auth.domain.entity.OAuth2Token;
import com.example.spotify.auth.domain.service.OAuth2TokenService;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OAuth2TokenAdapter implements OAuth2TokenService {


    @Override
    public OAuth2Token createFromSession(String accessToken, String refreshToken, Long expiryMillis) {
        if (accessToken == null || refreshToken == null || expiryMillis == null) {
            return null;
        }
        long expiresInSeconds = (expiryMillis - Instant.now().toEpochMilli()) / 1000;
        if (expiresInSeconds <= 0) return null;

        return OAuth2Token.create(accessToken, refreshToken, (int)expiresInSeconds);
    }
    @Override
    public OAuth2Token createFromOAuth2Token(OAuth2Token originalToken) {
        // Se precisar converter de OAuth2Token para UserToken
        return originalToken;
    }
}