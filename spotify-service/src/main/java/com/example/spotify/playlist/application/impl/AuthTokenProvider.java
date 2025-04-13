package com.example.spotify.playlist.application.impl;

import com.example.spotify.auth.domain.service.TokenStoragePort;
import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.playlist.application.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenProvider implements TokenProvider {

    private final TokenStoragePort tokenStorage;

    public AuthTokenProvider(TokenStoragePort tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public String getAccessToken() {
        UserToken token = tokenStorage.retrieveUserToken();
        return token != null ? token.getAccessToken() : null;
    }
}
