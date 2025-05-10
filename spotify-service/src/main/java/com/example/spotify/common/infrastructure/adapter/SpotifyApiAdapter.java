package com.example.spotify.common.infrastructure.adapter;


import com.example.spotify.auth.domain.entity.AuthenticationToken;
import com.example.spotify.auth.domain.service.AuthenticationPort;
import com.example.spotify.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.net.URI;

@Component
public class SpotifyApiAdapter extends ExternalServiceAdapter implements AuthenticationPort {

    private static final Logger log = LoggerFactory.getLogger(SpotifyApiAdapter.class);

    public SpotifyApiAdapter(SpotifyApi spotifyApi, SpotifyApiExceptionTranslator exceptionTralator) {
        super(spotifyApi, exceptionTralator);
    }

    @Override
    public URI createAuthorizationUri(String codeChallenge, String state, String scopes) {
                 AuthorizationCodeUriRequest request = spotifyApi.authorizationCodePKCEUri(codeChallenge)
                         .state(state)
                         .scope(scopes)
                         .build();

                 return executeSync(request::execute,
                "creating authorization URI"
                  );

    }
    @Override
    public AuthenticationToken exchangeCodeForToken(String code, String codeVerifier) {
               AuthorizationCodePKCERequest request = spotifyApi
                       .authorizationCodePKCE(code, codeVerifier)
                       .build();
               AuthorizationCodeCredentials credentials = executeSync(request::execute,
                       "exchanging code for token"
               );
               return AuthenticationToken.create(
                       credentials.getAccessToken(),
                       credentials.getRefreshToken(),
                       credentials.getExpiresIn()
               );



    }

}
