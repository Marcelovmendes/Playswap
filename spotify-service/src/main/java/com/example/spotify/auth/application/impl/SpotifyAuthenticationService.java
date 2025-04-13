package com.example.spotify.auth.application.impl;
import com.example.spotify.auth.application.AuthenticationService;
import com.example.spotify.auth.domain.entity.AuthenticationToken;
import com.example.spotify.auth.domain.service.StateManagementPort;
import com.example.spotify.auth.domain.service.AuthenticationPort;
import com.example.spotify.common.infrastructure.service.DefaultPkce;
import com.example.spotify.common.exception.ApplicationException;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
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
    private final StateManagementPort stateService;
    private final AuthenticationPort spotifyApiAdapter;
    private final DefaultPkce pkceConfig;

    private static final String SCOPES = "user-read-private user-read-email playlist-read-private playlist-read-collaborative user-library-read";


    public SpotifyAuthenticationService(DefaultPkce pkceConfig, StateManagementPort stateService, AuthenticationPort spotifyApiAdapter) {
        this.stateService = stateService;
        this.spotifyApiAdapter = spotifyApiAdapter;
        this.pkceConfig = pkceConfig;
    }

    @Override
    public URI initiateAuthentication() {
        try {
            String codeVerifier = pkceConfig.generateCodeVerifier();
            String codeChallenge = pkceConfig.generateCodeChallenge(codeVerifier);
            String state = UUID.randomUUID().toString().replace("-", "");

            log.info("Start authentication : {}", state);
            stateService.saveState(state, codeVerifier, STATE_TIMEOUT);
            return spotifyApiAdapter.createAuthorizationUri(codeChallenge, state, SCOPES);
        } catch (Exception e) {
            if (e instanceof ApplicationException) {
                throw e;
            }
            throw new AuthenticationException("Failed to initiate authentication", ErrorType.AUTHENTICATION_EXCEPTION);
         }
        }

    @Override
    public AuthenticationToken handleAuthenticationCallback(String code, String state) {
        if (code == null || state == null) throw new AuthenticationException("Invalid code or state provided",
                ErrorType.AUTHENTICATION_EXCEPTION);

        if(!stateService.validateState(state)) throw new AuthenticationException("Invalid state provided",
                ErrorType.AUTHENTICATION_EXCEPTION);

        String codeVerifier = stateService.getCodeVerifier(state);
        if(codeVerifier == null) throw new AuthenticationException("Invalid or Null code verifier provided",
                ErrorType.AUTHENTICATION_EXCEPTION);
        try {
            AuthenticationToken token = spotifyApiAdapter.exchangeCodeForToken(code, codeVerifier);
            stateService.removeState(state);
            return token;
        } catch (Exception e) {
            if (e instanceof ApplicationException) {
                throw e;
            }
            throw new AuthenticationException("Failed to exchange code for token", ErrorType.AUTHENTICATION_EXCEPTION);
        }
    }
}
