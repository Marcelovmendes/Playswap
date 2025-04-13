package com.example.spotify.common.infrastructure.adapter;


import com.example.spotify.auth.domain.entity.AuthenticationToken;
import com.example.spotify.auth.domain.service.AuthenticationPort;
import com.example.spotify.common.exception.ApplicationException;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import com.example.spotify.common.exception.SpotifyApiException;
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

    public SpotifyApiAdapter(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
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
         throw new SpotifyApiException("Error creating authorization URI", ErrorType.SPOTIFY_API_EXCEPTION);
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

           } catch (IOException e) {
               log.error("IO error exchanging code for token", e);
               throw new AuthenticationException("Error communicating with Spotify API", ErrorType.AUTHENTICATION_EXCEPTION);
           } catch (SpotifyWebApiException e) {
               log.error("Spotify API error rejecting code for token", e);
               throw new AuthenticationException("Spotify rejected the authorization code", ErrorType.AUTHENTICATION_EXCEPTION);
           } catch (ParseException e) {
               log.error("Parse error processing token response", e);
               throw new ApplicationException("Error parsing response from Spotify API", ErrorType.SPOTIFY_API_EXCEPTION);
           }

    }

}
