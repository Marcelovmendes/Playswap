package com.example.spotify.auth.infrastructure.service;

import com.example.spotify.auth.domain.entity.OAuth2Token;
import com.example.spotify.auth.domain.service.TokenStorageService;
import com.example.spotify.auth.infrastructure.adapter.OAuth2TokenAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SessionTokenStorageService implements TokenStorageService {

    private final HttpServletRequest request;
    private final OAuth2TokenAdapter tokenAdapter;
    private static final String TOKEN_KEY = "spotifyAccessToken";
    private static final String REFRESH_TOKEN_KEY = "spotifyRefreshToken";
    private static final String TOKEN_EXPIRY_KEY = "spotifyTokenExpiry";
    private static final Logger logger = LoggerFactory.getLogger(SessionTokenStorageService.class);

    public SessionTokenStorageService(HttpServletRequest request, OAuth2TokenAdapter tokenAdapter) {
        this.request = request;
        this.tokenAdapter = tokenAdapter;
    }

    @Override
    public void storeUserToken(HttpSession session, OAuth2Token token) {
        session.setAttribute(TOKEN_KEY, token.getAccessToken());
        session.setAttribute(REFRESH_TOKEN_KEY, token.getRefreshToken());
        session.setAttribute(TOKEN_EXPIRY_KEY, token.getExpiresAt().toEpochMilli());
    }

    @Override
    public OAuth2Token retrieveUserToken(String sessionId) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;

        String accessToken = (String) session.getAttribute(TOKEN_KEY);
        String refreshToken = (String) session.getAttribute(REFRESH_TOKEN_KEY);
        Long expiryMillis = (Long) session.getAttribute(TOKEN_EXPIRY_KEY);

        if (accessToken == null || refreshToken == null || expiryMillis == null) return null;

        return tokenAdapter.createFromSession(accessToken, refreshToken, expiryMillis);
    }

    @Override
    public boolean hasValidToken(String sessionId) {
        OAuth2Token token = retrieveUserToken(sessionId);
        return token != null && !token.isExpired();
    }

    @Override
    public void removeUserToken(String sessionId) {
        HttpSession session = request.getSession(false);
        if (session == null) {
          logger.warn("session is null");
        }

    }
}
