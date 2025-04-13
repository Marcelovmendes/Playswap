package com.example.spotify.user.application.impl;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import com.example.spotify.common.exception.UserProfileException;
import com.example.spotify.user.api.dto.UserProfileDTO;
import com.example.spotify.user.domain.UserProfilePort;
import com.example.spotify.user.application.UserServicePort;
import com.example.spotify.user.domain.entity.User;
import com.example.spotify.user.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServicePort {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserProfilePort spotifyUserAdapter;
    private final UserRepository repository;

    public UserServiceImpl(UserProfilePort spotifyUserAdapter, UserRepository repository) {
        this.spotifyUserAdapter = spotifyUserAdapter;
        this.repository = repository;
    }

    @Override
    public UserProfileDTO getCurrentUserProfileAsync(UserToken token)  {
         if (token == null || token.getAccessToken() == null) throw new AuthenticationException("Invalid token provided",
                  ErrorType.AUTHENTICATION_EXCEPTION);

         User userData = spotifyUserAdapter.getCurrentUsersProfileAsync(token);

         if(userData.getEmail() == null || userData.getEmail().isEmpty()) {
             throw new UserProfileException("Email not found in Spotify user data",
                     ErrorType.RESOURCE_NOT_FOUND_EXCEPTION);
         }
         User persistedUser = repository.findByEmail(userData.getEmail()).orElse(userData);

         if(userData == persistedUser)
             try {
                    repository.save(persistedUser);
                } catch (Exception e) {
                    logger.error("Error saving user data: {}", e.toString());
             }

         return convertToProfileDTO(persistedUser);

    }
    private UserProfileDTO convertToProfileDTO(User user) {

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