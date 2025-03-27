package com.example.spotify.user.application.impl;

import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.user.api.dto.UserProfileDTO;
import com.example.spotify.user.application.CurrentUserService;
import com.example.spotify.user.application.UserServiceContract;
import com.example.spotify.user.domain.entity.SpotifyUser;
import com.example.spotify.user.domain.repository.SpotifyUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceContract {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final CurrentUserService spotifyUserAdapter;
    private final SpotifyUserRepository repository;

    public UserServiceImpl(CurrentUserService spotifyUserAdapter, SpotifyUserRepository repository) {
        this.spotifyUserAdapter = spotifyUserAdapter;
        this.repository = repository;
    }

    @Override
    public UserProfileDTO getCurrentUserProfileAsync(UserTokenService token)  {
         SpotifyUser spotifyUserData = spotifyUserAdapter.getCurrentUsersProfileAsync(token);
         SpotifyUser spotifyUser = repository.findByEmail(spotifyUserData.getEmail()).orElse(spotifyUserData);

         return convertToProfileDTO(spotifyUser);

    }
    private UserProfileDTO convertToProfileDTO(SpotifyUser user) {

       return new UserProfileDTO(
                user.getBirthdate(),
                user.getCountry(),
                user.getDisplayName(),
                user.getEmail(),
                user.getExternalUrls(),
                user.getFollowersCount(),
                user.getHref(),
                user.getPhotoCover(),
                user.getSpotifyUri(),
                user.getType()

        );

    }
}