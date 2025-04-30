package com.example.spotify.common.infrastructure.adapter;


import com.example.spotify.auth.domain.entity.AuthenticationToken;
import com.example.spotify.auth.domain.service.AuthenticationPort;
import com.example.spotify.common.exception.*;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.io.IOException;
import java.net.URI;

@Component
public class SpotifyApiAdapter implements AuthenticationPort {

    private static final Logger log = LoggerFactory.getLogger(SpotifyApiAdapter.class);
    private final SpotifyApi spotifyApi;
    private final SpotifyApiExceptionTranslator exceptionTralator;

    public SpotifyApiAdapter(SpotifyApi spotifyApi, SpotifyApiExceptionTranslator exceptionTralator) {
        this.spotifyApi = spotifyApi;
        this.exceptionTralator = exceptionTralator;
    }

    @Override
    public URI createAuthorizationUri(String codeChallenge, String state, String scopes) {
     try {
         return spotifyApi.authorizationCodePKCEUri(codeChallenge)
                 .state(state)
                 .scope(scopes)
                 .build()
                 .execute();
     } catch (Exception e) {
         log.error("Error creating authorization URI", e);
         throw exceptionTralator.translate(e);
     }

    }
    @Override
    public AuthenticationToken exchangeCodeForToken(String code, String codeVerifier) {
           try {
               AuthorizationCodePKCERequest request = spotifyApi.
                       authorizationCodePKCE(code, codeVerifier)
                       .build();
               AuthorizationCodeCredentials credentials = request.execute();
               return AuthenticationToken.create(
                       credentials.getAccessToken(),
                       credentials.getRefreshToken(),
                       credentials.getExpiresIn()
               );

           } catch (Exception e) {
               log.error("Error exchanging code for token", e);
               throw exceptionTralator.translate(e);
           }

    }

}
