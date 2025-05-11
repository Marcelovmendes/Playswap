package com.example.spotify.user.application.impl;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import com.example.spotify.common.exception.UserProfileException;
import com.example.spotify.common.infrastructure.service.TokenProvider;
import com.example.spotify.user.api.dto.UserProfileDTO;
import com.example.spotify.user.domain.UserProfilePort;
import com.example.spotify.user.application.UserService;
import com.example.spotify.user.domain.entity.Email;
import com.example.spotify.user.domain.entity.UserEntity;
import com.example.spotify.user.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserProfilePort userProfilePort;
    private final UserRepository repository;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserProfilePort userProfilePort, UserRepository repository, TokenProvider tokenProvider) {
        this.userProfilePort = userProfilePort;
        this.repository = repository;
        this.tokenProvider = tokenProvider;
    }


    @Override
    public UserProfileDTO getCurrentUserProfileAsync()  {
        if(!tokenProvider.isTokenValid()){
            logger.error("Token de acesso inválido");
            throw new AuthenticationException("Token de acesso inválido", ErrorType.SERVER_ERROR);
        }
         String accessToken = tokenProvider.getAccessToken();
         UserEntity userData = userProfilePort.getCurrentUsersProfileAsync(accessToken);
         Email email = userData.getEmail();

         if(email.isValid()) {
             throw new UserProfileException("Email not found in Spotify user data",
                     ErrorType.RESOURCE_NOT_FOUND_EXCEPTION);
         }
        try {
            // Agora usamos o repositório do domínio
            UserEntity persistedUser = repository.findByEmail(userData.getEmailAddress())
                    .orElseGet(() -> {
                        try {
                            return repository.save(userData);
                        } catch (ExecutionException e) {
                            throw new UserProfileException("Erro ao salvar dados do usuário", ErrorType.SERVER_ERROR);
                        }
                    });

            return mapToDTObject(persistedUser);
        } catch (Exception e) {
            logger.error("Erro ao processar perfil do usuário", e);
            throw new UserProfileException("Erro ao processar perfil do usuário", ErrorType.SERVER_ERROR);
        }

    }
    private UserProfileDTO mapToDTObject(UserEntity user) {

       return new UserProfileDTO(
                user.getBirthdate(),
                user.getCountry(),
                user.getDisplayName(),
                user.getEmailAddress(),
                user.getExternalUrls(),
                user.getFollowersCount(),
                user.getHref(),
                user.getPhotoCover(),
                user.getSpotifyUri(),
                user.getType()

        );
    }
}