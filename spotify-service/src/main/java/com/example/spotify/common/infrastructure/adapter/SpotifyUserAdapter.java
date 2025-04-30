package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import com.example.spotify.common.exception.SpotifyApiException;
import com.example.spotify.common.exception.SpotifyApiExceptionTranslator;
import com.example.spotify.user.domain.UserProfilePort;
import com.example.spotify.user.domain.entity.Email;
import com.example.spotify.user.domain.entity.User;
import com.example.spotify.user.domain.entity.UserId;
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
    private final SpotifyApiExceptionTranslator exceptionTranslator;

    public SpotifyUserAdapter(SpotifyApi spotifyApi, SpotifyApiExceptionTranslator exceptionTranslator) {
        this.spotifyApi = spotifyApi;
        this.exceptionTranslator = exceptionTranslator;
    }

    @Override
    public se.michaelthelin.spotify.model_objects.specification.User getCurrentUsersProfileSync(UserToken tokenAccess){
        try {
        spotifyApi.setAccessToken(tokenAccess.getAccessToken());
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();

            return request.execute();
        } catch (Exception e) {
            log.error("Error exchanging code for token", e);
            throw exceptionTranslator.translate(e);
        }
    }

    @Override
    public User getCurrentUsersProfileAsync(UserToken tokenAccess) {
        spotifyApi.setAccessToken(tokenAccess.getAccessToken());
        GetCurrentUsersProfileRequest user = spotifyApi.getCurrentUsersProfile().build();

        return convertUserToUserEntity(user.executeAsync().join());
    }


    private User convertUserToUserEntity(se.michaelthelin.spotify.model_objects.specification.User spotifyUser) {
        LocalDate birthdate = null;
        if (spotifyUser.getBirthdate() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                birthdate = LocalDate.parse(spotifyUser.getBirthdate(), formatter);
            } catch (Exception e) {
                log.warn("Data de nascimento não pôde ser parseada: {}", spotifyUser.getBirthdate());
            }
        }
        String photoUrl = null;
        if (spotifyUser.getImages() != null && spotifyUser.getImages().length > 0) {
            photoUrl = spotifyUser.getImages()[0].getUrl();
        }

        return new User(
                UserId.generate(),
                birthdate,
                spotifyUser.getCountry() != null ? spotifyUser.getCountry().getAlpha3() : null,
                spotifyUser.getDisplayName(),
                Email.of(spotifyUser.getEmail()),
                spotifyUser.getExternalUrls().get("spotify"),
                spotifyUser.getFollowers() != null ? spotifyUser.getFollowers().getTotal() : 0,
                spotifyUser.getHref(),
                photoUrl,
                spotifyUser.getUri(),
                spotifyUser.getType().getType(),
                spotifyUser.getId() // Spotify ID
        );

    }

}