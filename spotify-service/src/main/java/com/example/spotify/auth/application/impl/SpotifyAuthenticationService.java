package com.example.spotify.auth.application.impl;
import com.example.spotify.auth.application.AuthenticationService;
import com.example.spotify.auth.domain.entity.OAuth2Token;
import com.example.spotify.auth.domain.service.PkceService;
import com.example.spotify.auth.domain.service.StateManagementService;
import com.example.spotify.auth.infrastructure.service.SpotifyApiContract;
import com.example.spotify.common.exception.ApplicationException;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Duration;
import java.util.UUID;

@Service
public class SpotifyAuthenticationService implements AuthenticationService {


    private static final Logger log = LoggerFactory.getLogger(SpotifyAuthenticationService.class);
    private static final Duration STATE_TIMEOUT = Duration.ofMinutes(10);
    private final StateManagementService stateService;
    private final SpotifyApiContract spotifyApiAdapter;
    private final PkceService pkceService;

    private static final String SCOPES = "user-read-private user-read-email playlist-read-private playlist-read-collaborative user-library-read";


    public SpotifyAuthenticationService(PkceService pkceService, StateManagementService stateService, SpotifyApiContract spotifyApiAdapter, PkceService pkceService1) {
        this.stateService = stateService;
        this.spotifyApiAdapter = spotifyApiAdapter;
        this.pkceService = pkceService1;
    }

    @Override
    public URI initiateAuthentication() {
        try {
            String codeVerifier = pkceService.generateCodeVerifier();
            String codeChallenge = pkceService.generateCodeChallenge(codeVerifier);
            String state = UUID.randomUUID().toString().replace("-", "");

            log.info("Iniciando autenticação Spotify: {}", state);
            stateService.saveState(state, codeVerifier, STATE_TIMEOUT);
            return spotifyApiAdapter.createAuthorizationUri(codeChallenge, state, SCOPES);
        } catch (Exception e) {
            if (e instanceof ApplicationException) {
                throw e;
            }
            throw new AuthenticationException("Failed to initiate authentication", ExceptionType.AUTHENTICATION_EXCEPTION);
         }
        }

    @Override
    public OAuth2Token handleAuthenticationCallback(String code, String state) {
        if (code == null || state == null) throw new AuthenticationException("Invalid code or state provided",
                ExceptionType.AUTHENTICATION_EXCEPTION);

        if(!stateService.validateState(state)) throw new AuthenticationException("Invalid state provided",
                ExceptionType.AUTHENTICATION_EXCEPTION);

        String codeVerifier = stateService.getCodeVerifier(state);
        if(codeVerifier == null) throw new AuthenticationException("Invalid or Null code verifier provided",
                ExceptionType.AUTHENTICATION_EXCEPTION);
        try {
            OAuth2Token token = spotifyApiAdapter.exchangeCodeForToken(code, codeVerifier);
            stateService.removeState(state);
            return token;
        } catch (Exception e) {
            if (e instanceof ApplicationException) {
                throw e;
            }
            throw new AuthenticationException("Failed to exchange code for token", ExceptionType.AUTHENTICATION_EXCEPTION);
        }
    }
}
