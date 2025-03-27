package com.example.spotify.auth.domain.service;

import com.example.spotify.auth.domain.entity.OAuth2Token;
import jakarta.servlet.http.HttpSession;

public interface TokenStorageService {
    void storeUserToken(HttpSession session, UserTokenService token);
    UserTokenService retrieveUserToken();
    void removeUserToken(String sessionId);
}
