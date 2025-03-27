package com.example.spotify.auth.infrastructure.adapter;

import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.user.application.CurrentUserService;
import com.example.spotify.user.domain.entity.SpotifyUser;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SpotifyUserAdapter implements CurrentUserService {

    private final SpotifyApi spotifyApi;

    public SpotifyUserAdapter(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public User getCurrentUsersProfileSync(UserTokenService tokenAccess) throws IOException, ParseException, SpotifyWebApiException {
        spotifyApi.setAccessToken(tokenAccess.getAccessToken());
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
        try {
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public SpotifyUser getCurrentUsersProfileAsync(UserTokenService tokenAccess) {
        spotifyApi.setAccessToken(tokenAccess.getAccessToken());
        GetCurrentUsersProfileRequest user = spotifyApi.getCurrentUsersProfile().build();

        return convertUserToSpotifyUserEntity(user.executeAsync().join());
    }

    private SpotifyUser convertUserToSpotifyUserEntity(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(user.getBirthdate(), formatter);

        return new SpotifyUser(
                user.getId(),
                birthdate,
                user.getCountry().getAlpha3(),
                user.getDisplayName(),
                user.getEmail(),
                user.getExternalUrls().toString(),
                user.getFollowers().getTotal(),
                user.getHref(),
                user.getImages()[0].getUrl(),
                user.getUri(),
                user.getType().getType()
        );
    }

}
