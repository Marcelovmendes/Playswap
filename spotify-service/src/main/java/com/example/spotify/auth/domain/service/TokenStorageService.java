package com.example.spotify.auth.domain.service;

import com.example.spotify.auth.domain.entity.OAuth2Token;
import jakarta.servlet.http.HttpSession;

public interface TokenStorageService {
    void storeUserToken(HttpSession session, OAuth2Token token);
    UserTokenService retrieveUserToken(String sessionId);
    boolean hasValidToken(String sessionId);
    void removeUserToken(String sessionId);
}
