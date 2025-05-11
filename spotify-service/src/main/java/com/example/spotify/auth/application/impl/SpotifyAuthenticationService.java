package com.example.spotify.auth.application.impl;

import com.example.spotify.auth.application.AuthUseCase;
import com.example.spotify.auth.application.OAuthClient;
import com.example.spotify.auth.domain.entity.AuthState;
import com.example.spotify.auth.domain.entity.AuthorizationResquest;
import com.example.spotify.auth.domain.entity.Token;
import com.example.spotify.auth.domain.repository.AuthStateRepository;
import com.example.spotify.auth.domain.service.PkceGenerator;
import com.example.spotify.auth.domain.service.StateManagementPort;
import com.example.spotify.auth.domain.service.AuthenticationPort;
import com.example.spotify.common.infrastructure.service.DefaultPkceAdapter;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpotifyAuthenticationService implements AuthUseCase {


    private static final Logger log = LoggerFactory.getLogger(SpotifyAuthenticationService.class);
    private static final Duration STATE_TIMEOUT = Duration.ofMinutes(10);
    private final StateManagementPort stateService;
    private final AuthenticationPort spotifyApiAdapter;
    private final DefaultPkceAdapter pkceConfig;

    private final AuthStateRepository authStateRepository;
    private final OAuthClient oauthClient;
    private final PkceGenerator pkceGenerator;

    private static final String SCOPES = "user-read-private user-read-email playlist-read-private playlist-read-collaborative user-library-read";


    public SpotifyAuthenticationService(DefaultPkceAdapter pkceConfig, StateManagementPort stateService, AuthenticationPort spotifyApiAdapter, AuthStateRepository authStateRepository, OAuthClient oauthClient, PkceGenerator pkceGenerator) {
        this.stateService = stateService;
        this.spotifyApiAdapter = spotifyApiAdapter;
        this.pkceConfig = pkceConfig;
        this.authStateRepository = authStateRepository;
        this.oauthClient = oauthClient;
        this.pkceGenerator = pkceGenerator;
    }

    @Override
    public URI initiateAuthentication() {
        try {
            String codeVerifier = pkceGenerator.generateCodeVerifier();
            String codeChallenge = pkceGenerator.generateCodeChallenge(codeVerifier);
            String stateValue = UUID.randomUUID().toString().replace("-", "");

            AuthState state = AuthState.create(stateValue, codeVerifier);
            authStateRepository.save(state, codeVerifier, STATE_TIMEOUT);

            AuthorizationResquest request = AuthorizationResquest.create(stateValue, codeChallenge, Collections.singleton(SCOPES));

            return spotifyApiAdapter.createAuthorizationUri(codeChallenge,state.toString(), SCOPES);
        } catch (Exception e) {
            log.error("Failed to initiate authentication", e);
            throw new AuthenticationException("Failed to initiate authentication", ErrorType.AUTHENTICATION_EXCEPTION);
         }
        }

    @Override
    public Token completeAuthentication(String code, String stateValue) {
        if (code == null || stateValue == null) throw new AuthenticationException("Invalid code or stateValue provided",
                ErrorType.AUTHENTICATION_EXCEPTION);

        Optional<AuthState> state = authStateRepository.findCodeVerifier(stateValue);
        if(state.isEmpty()) throw new AuthenticationException("Invalid stateValue provided",
                ErrorType.AUTHENTICATION_EXCEPTION);
        try {
            Token token = oauthClient.exchangeCodeForToken(code, state.get().getCodeVerifier());
            state.ifPresent(authStateRepository::remove);
            return token;
        } catch (Exception e) {
            log.error("Failed to exchange code for token", e);
            throw new AuthenticationException("Failed to exchange code for token", ErrorType.AUTHENTICATION_EXCEPTION);
        }
    }
}
