package com.example.spotify.auth.domain.service;

import jakarta.servlet.http.HttpSession;

public interface TokenStoragePort {
    void storeUserToken(HttpSession session, UserToken token);
    UserToken retrieveUserToken();
    void removeUserToken(String sessionId);
}
