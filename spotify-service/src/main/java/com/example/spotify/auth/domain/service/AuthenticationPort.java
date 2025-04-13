package com.example.spotify.auth.domain.service;

import com.example.spotify.auth.domain.entity.AuthenticationToken;

import java.net.URI;

public interface AuthenticationPort {
    URI createAuthorizationUri(String codeChallenge, String state, String scopes);
    AuthenticationToken exchangeCodeForToken(String code, String codeVerifier);
}
