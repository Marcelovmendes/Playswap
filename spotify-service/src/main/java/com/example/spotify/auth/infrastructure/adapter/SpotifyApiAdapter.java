package com.example.spotify.auth.infrastructure.adapter;


import com.example.spotify.auth.domain.entity.OAuth2Token;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import javax.naming.AuthenticationException;
import java.net.URI;

@Component
public class SpotifyApiAdapter {

    private final SpotifyApi spotifyApi;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    public SpotifyApiAdapter(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }
    public URI createAuthorizationUri(String codeChallenge, String state, String scopes) throws AuthenticationException {
            return spotifyApi.authorizationCodePKCEUri(codeChallenge)
                    .state(state)
                    .scope(scopes)
                    .build()
                    .execute();

    }

    public OAuth2Token exchangeCodeForToken(String code, String codeVerifier) throws AuthenticationException {
           try {
               AuthorizationCodePKCERequest request = spotifyApi.
                       authorizationCodePKCE(code, codeVerifier)
                       .build();

               AuthorizationCodeCredentials credentials = request.execute();
               return OAuth2Token.create(
                       credentials.getAccessToken(),
                       credentials.getRefreshToken(),
                       credentials.getExpiresIn()
               );

           } catch (Exception e) {
               throw new AuthenticationException();
           }

    }
}
