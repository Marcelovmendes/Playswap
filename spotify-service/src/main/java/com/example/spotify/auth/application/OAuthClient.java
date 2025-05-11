package com.example.spotify.auth.application;

import com.example.spotify.auth.domain.entity.AuthorizationResquest;
import com.example.spotify.auth.domain.entity.Token;

import java.net.URI;

public interface OAuthClient {
    URI createAuthorizationUri(AuthorizationResquest request);
    Token exchangeCodeForToken(String code, String codeVerifier);
    Token refreshAccessToken(String refreshToken);
}
