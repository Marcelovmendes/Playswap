package com.example.spotify.user.application.impl;

import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ExceptionType;
import com.example.spotify.common.exception.UserProfileException;
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
         if (token == null || token.getAccessToken() == null) throw new AuthenticationException("Invalid token provided",
                  ExceptionType.AUTHENTICATION_EXCEPTION);

         SpotifyUser spotifyUserData = spotifyUserAdapter.getCurrentUsersProfileAsync(token);

         if(spotifyUserData.getEmail() == null || spotifyUserData.getEmail().isEmpty()) {
             throw new UserProfileException("Email not found in Spotify user data",
                     ExceptionType.USER_NOT_FOUND);
         }
         SpotifyUser spotifyUser = repository.findByEmail(spotifyUserData.getEmail()).orElse(spotifyUserData);

         if(spotifyUserData == spotifyUser)
             try {
                    repository.save(spotifyUser);
                } catch (Exception e) {
                    logger.error("Error saving user data: {}", e.getMessage());
                    throw new UserProfileException("Error saving user data",
                            ExceptionType.GENERAL_EXCEPTION);
             }

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