package com.example.spotify.auth.domain.service;

import com.example.spotify.auth.domain.entity.Token;

import java.net.URI;

public interface AuthenticationPort {
    URI createAuthorizationUri(String codeChallenge, String state, String scopes);
    Token exchangeCodeForToken(String code, String codeVerifier);
}
