package com.example.spotify.auth.infrastructure.adapter;


import com.example.spotify.auth.domain.entity.OAuth2Token;
import com.example.spotify.auth.infrastructure.service.SpotifyApiContract;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ExceptionType;
import com.example.spotify.common.exception.SpotifyApiException;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.io.IOException;
import java.net.URI;

@Component
public class SpotifyApiAdapter implements SpotifyApiContract {

    private static final Logger log = LoggerFactory.getLogger(SpotifyApiAdapter.class);
    private final SpotifyApi spotifyApi;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

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
         throw new SpotifyApiException("Error creating authorization URI", ExceptionType.SPOTIFY_API_EXCEPTION);
     }

    }
    @Override
    public OAuth2Token exchangeCodeForToken(String code, String codeVerifier) {
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

           } catch (IOException e) {
               log.error(e.getMessage(), e);
               throw new AuthenticationException("Error exchanging code for token",ExceptionType.AUTHENTICATION_EXCEPTION);
           } catch (SpotifyWebApiException e) {
               log.error(e.getMessage(),e);
               throw new AuthenticationException("Error rejecting code for token",ExceptionType.AUTHENTICATION_EXCEPTION);
              } catch (ParseException e) {
               log.error(e.getMessage(), e);
               throw new SpotifyApiException("Error parsing code for token",ExceptionType.SPOTIFY_API_EXCEPTION);
           }

    }

    public Playlist getCurrentPlaylist(String accessToken) throws IOException, ParseException, SpotifyWebApiException {
          setAccessToken(accessToken);
          log.info("Buscando playlists");
          return spotifyApi.getPlaylist(accessToken).build().execute();

    }
    private void setAccessToken(String accessToken) {
        spotifyApi.setAccessToken(accessToken);
    }

}
