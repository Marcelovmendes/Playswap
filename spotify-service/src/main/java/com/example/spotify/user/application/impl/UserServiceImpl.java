package com.example.spotify.user.application.impl;

import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.user.application.CurrentUserService;
import com.example.spotify.user.application.UserServiceContract;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class UserServiceImpl implements UserServiceContract {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final CurrentUserService spotifyUserAdapter;

    public UserServiceImpl(CurrentUserService spotifyUserAdapter) {
        this.spotifyUserAdapter = spotifyUserAdapter;
    }

    @Override
    public CompletableFuture<User> getCurrentUserProfileAsync(UserTokenService accessToken) throws IOException, ParseException, SpotifyWebApiException {

        return spotifyUserAdapter.getCurrentUsersProfileAsync(accessToken)
                .exceptionally(ex -> {
                    logger.error("Erro ao obter perfil do usuário: {}", ex.getMessage(), ex);
                    throw new CompletionException("Falha ao obter perfil do usuário do Spotify", ex);
                });


    }
}