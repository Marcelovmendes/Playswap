package com.example.spotify.auth.domain.service;

import com.example.spotify.auth.domain.entity.AuthenticationToken;

public interface OAuth2TokenPort {
    AuthenticationToken createFromSession(String accessToken, String refreshToken, Long expiryMillis);
    AuthenticationToken createFromOAuth2Token(AuthenticationToken originalToken);
}
