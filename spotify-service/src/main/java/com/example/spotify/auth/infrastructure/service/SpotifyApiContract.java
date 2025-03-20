package com.example.spotify.auth.infrastructure.service;

import com.example.spotify.auth.domain.entity.OAuth2Token;

import javax.naming.AuthenticationException;
import java.net.URI;

public interface SpotifyApiContract {
    URI createAuthorizationUri(String codeChallenge, String state, String scopes);
    OAuth2Token exchangeCodeForToken(String code, String codeVerifier) throws AuthenticationException;
}
