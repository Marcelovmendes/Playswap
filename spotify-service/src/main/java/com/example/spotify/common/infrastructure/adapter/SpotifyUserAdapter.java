package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import com.example.spotify.common.exception.SpotifyApiException;
import com.example.spotify.user.domain.UserProfilePort;
import com.example.spotify.user.domain.entity.User;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SpotifyUserAdapter implements UserProfilePort {

    private final static Logger log = LoggerFactory.getLogger(SpotifyUserAdapter.class);

    private final SpotifyApi spotifyApi;

    public SpotifyUserAdapter(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public se.michaelthelin.spotify.model_objects.specification.User getCurrentUsersProfileSync(UserToken tokenAccess){
        try {
        spotifyApi.setAccessToken(tokenAccess.getAccessToken());
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();

            return request.execute();
        } catch (IOException e) {
            log.error("Communication error when retrieving profile {}", e.getMessage());
            throw new SpotifyApiException("Communication error with Spotify API",
                    ErrorType.SPOTIFY_API_EXCEPTION);
        } catch (SpotifyWebApiException e) {
            if (e.getMessage().contains("401")) {
                log.warn("Invalid Token {}", e.getMessage());
                throw new AuthenticationException("Token expired or invalid",
                        ErrorType.TOKEN_EXPIRED);
            }
            log.error("SpotifyApi Error: {}", e.getMessage());
            throw new SpotifyApiException("Spotify API error",
                    ErrorType.SPOTIFY_API_EXCEPTION);
        } catch (ParseException e) {
            log.error("Error processing response: {}", e.getMessage());
            throw new SpotifyApiException("Invalid response from Spotify API",
                    ErrorType.SPOTIFY_API_EXCEPTION);
        }
    }

    @Override
    public User getCurrentUsersProfileAsync(UserToken tokenAccess) {
        spotifyApi.setAccessToken(tokenAccess.getAccessToken());
        GetCurrentUsersProfileRequest user = spotifyApi.getCurrentUsersProfile().build();

        return convertUserToSpotifyUserEntity(user.executeAsync().join());
    }

    private User convertUserToSpotifyUserEntity(se.michaelthelin.spotify.model_objects.specification.User user) {
        LocalDate birthdate = null;
        if (user.getBirthdate() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                birthdate = LocalDate.parse(user.getBirthdate(), formatter);
            } catch (Exception e) {
                log.warn("Data de nascimento não pôde ser parseada: {}", user.getBirthdate());
            }
        }
        String photoUrl = null;
        if (user.getImages() != null && user.getImages().length > 0) {
            photoUrl = user.getImages()[0].getUrl();
        }
        /***
        return new User(
                birthdate,
                user.getCountry().getAlpha3(),
                user.getDisplayName(),
                user.getEmail(),
                user.getExternalUrls().toString(),
                user.getFollowers().getTotal(),
                user.getHref(),
                photoUrl,
                user.getUri(),
                user.getType().getType(),
                user.getId()
        );
         ***/
        return null;
    }

}