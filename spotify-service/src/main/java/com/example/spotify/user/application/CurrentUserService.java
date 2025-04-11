package com.example.spotify.user.application;

import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.user.domain.entity.SpotifyUser;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface CurrentUserService {
    User getCurrentUsersProfileSync(UserTokenService userToken);
    SpotifyUser getCurrentUsersProfileAsync(UserTokenService token);
}
