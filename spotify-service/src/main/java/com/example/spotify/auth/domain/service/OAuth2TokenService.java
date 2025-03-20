package com.example.spotify.auth.domain.service;

import com.example.spotify.auth.domain.entity.OAuth2Token;

public interface OAuth2TokenService {
    OAuth2Token createFromSession(String accessToken, String refreshToken, Long expiryMillis);
    OAuth2Token createFromOAuth2Token(OAuth2Token originalToken);
}
