package com.example.spotify.common.infrastructure.service;

import com.example.spotify.auth.domain.service.TokenStoragePort;
import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.common.infrastructure.adapter.OAuth2TokenAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SessionTokenStorage implements TokenStoragePort {

    private final HttpServletRequest request;
    private final OAuth2TokenAdapter tokenAdapter;
    private static final String TOKEN_KEY = "spotifyAccessToken";
    private static final String REFRESH_TOKEN_KEY = "spotifyRefreshToken";
    private static final String TOKEN_EXPIRY_KEY = "spotifyTokenExpiry";
    private static final Logger logger = LoggerFactory.getLogger(SessionTokenStorage.class);

    public SessionTokenStorage(HttpServletRequest request, OAuth2TokenAdapter tokenAdapter) {
        this.request = request;
        this.tokenAdapter = tokenAdapter;
    }

    @Override
    public void storeUserToken(HttpSession session, UserToken token) {
        session.setAttribute(TOKEN_KEY, token.getAccessToken());
        session.setAttribute(REFRESH_TOKEN_KEY, token.getRefreshToken());
        session.setAttribute(TOKEN_EXPIRY_KEY, token.getExpiresAt().toEpochMilli());
    }

    @Override
    public UserToken retrieveUserToken() {
        HttpSession session = request.getSession(false);
        if (session == null) return null;

        String accessToken = (String) session.getAttribute(TOKEN_KEY);
        String refreshToken = (String) session.getAttribute(REFRESH_TOKEN_KEY);
        Long expiryMillis = (Long) session.getAttribute(TOKEN_EXPIRY_KEY);

        if (accessToken == null || refreshToken == null || expiryMillis == null) return null;

        return tokenAdapter.createFromSession(accessToken, refreshToken, expiryMillis);
    }


    @Override
    public void removeUserToken(String sessionId) {
        HttpSession session = request.getSession(false);
        if (session == null) {
          logger.warn("session is null");
        }

    }
}
