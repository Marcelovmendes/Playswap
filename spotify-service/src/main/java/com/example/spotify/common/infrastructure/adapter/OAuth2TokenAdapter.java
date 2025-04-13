package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.auth.domain.entity.AuthenticationToken;
import com.example.spotify.auth.domain.service.OAuth2TokenPort;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OAuth2TokenAdapter implements OAuth2TokenPort {


    @Override
    public AuthenticationToken createFromSession(String accessToken, String refreshToken, Long expiryMillis) {
        if (accessToken == null || refreshToken == null || expiryMillis == null) {
            return null;
        }
        long expiresInSeconds = (expiryMillis - Instant.now().toEpochMilli()) / 1000;
        if (expiresInSeconds <= 0) return null;

        return AuthenticationToken.create(accessToken, refreshToken, (int)expiresInSeconds);
    }
    @Override
    public AuthenticationToken createFromOAuth2Token(AuthenticationToken originalToken) {
        // Se precisar converter de OAuth2Token para UserToken
        return originalToken;
    }
}