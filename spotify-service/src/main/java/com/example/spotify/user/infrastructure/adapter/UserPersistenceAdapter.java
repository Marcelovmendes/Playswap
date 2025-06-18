package com.example.spotify.user.infrastructure.adapter;

import com.example.spotify.user.infrastructure.persistence.UserJdbcEntity;
import com.example.spotify.user.infrastructure.UserJdbcRepository;
import com.example.spotify.user.domain.entity.Email;
import com.example.spotify.user.domain.entity.UserEntity;
import com.example.spotify.user.domain.entity.UserId;
import com.example.spotify.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class UserPersistenceAdapter implements UserRepository {

    private final UserJdbcRepository jdbcRepository;
    public UserPersistenceAdapter(UserJdbcRepository userRepository) {
        this.jdbcRepository = userRepository;
    }
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return jdbcRepository.findByEmail(email)
                .map(this::adaptToDomainEntity);
    }

    @Override
    public UserEntity save(UserEntity user) throws ExecutionException {
       try {
           UserJdbcEntity jdbcEntity = adaptToJdbcEntity(user);
           UserJdbcEntity savedEntity = jdbcRepository.save(jdbcEntity);
           return adaptToDomainEntity(savedEntity);
       } catch (Exception e) {
           throw new ExecutionException(e);
       }
    }
    private UserEntity adaptToDomainEntity(UserJdbcEntity userJdbcEntity){
        return new UserEntity(
                UserId.fromInternalId(userJdbcEntity.getId()),
                userJdbcEntity.getBirthdate(),
                userJdbcEntity.getCountry(),
                userJdbcEntity.getDisplayName(),
                new Email(userJdbcEntity.getEmail()),
                userJdbcEntity.getExternalUrls(),
                userJdbcEntity.getFollowersCount(),
                userJdbcEntity.getHref(),
                userJdbcEntity.getPhotoCover(),
                userJdbcEntity.getSpotifyUri(),
                userJdbcEntity.getType(),
                userJdbcEntity.getUserRegisteredId()
        );


    }

    private UserJdbcEntity adaptToJdbcEntity(UserEntity user){

        return new UserJdbcEntity(
                user.getId().getInternalId(),
                user.getBirthdate(),
                user.getCountry(),
                user.getDisplayName(),
                user.getEmailAddress(),
                user.getExternalUrls(),
                user.getFollowersCount(),
                user.getHref(),
                user.getPhotoCover(),
                user.getSpotifyUri(),
                user.getType(),
                user.getUserRegisteredId()
        );

    }

}
