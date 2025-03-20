package com.example.spotify.auth.domain.entity;

import com.example.spotify.auth.domain.service.UserTokenService;

import java.time.Instant;

public class OAuth2Token implements UserTokenService {

    private final String accessToken;
    private final String refreshToken;
    private final Instant expiresAt;

    private OAuth2Token(String accessToken, String refreshToken, Instant expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    public static OAuth2Token create(String accessToken, String refreshToken, int expiresIn) {
        Instant expiresAt = Instant.now().plusSeconds(expiresIn);
        return new OAuth2Token(accessToken, refreshToken, expiresAt);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

}
