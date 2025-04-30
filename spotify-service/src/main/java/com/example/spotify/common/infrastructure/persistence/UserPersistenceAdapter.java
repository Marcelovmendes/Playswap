package com.example.spotify.common.infrastructure.persistence;

import com.example.spotify.common.infrastructure.repository.UserJdbcRepository;
import com.example.spotify.user.domain.entity.Email;
import com.example.spotify.user.domain.entity.User;
import com.example.spotify.user.domain.entity.UserId;
import com.example.spotify.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.zip.DataFormatException;

@Component
public class UserPersistenceAdapter implements UserRepository {

    private final UserJdbcRepository jdbcRepository;
    public UserPersistenceAdapter(UserJdbcRepository userRepository) {
        this.jdbcRepository = userRepository;
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcRepository.findByEmail(email)
                .map(this::adaptToDomainEntity);
    }

    @Override
    public User save(User user) throws ExecutionException {
       try {
           UserJdbcEntity jdbcEntity = adaptToJdbcEntity(user);
           UserJdbcEntity savedEntity = jdbcRepository.save(jdbcEntity);
           return adaptToDomainEntity(savedEntity);
       } catch (Exception e) {
           throw new ExecutionException(e);
       }
    }
    private User adaptToDomainEntity( UserJdbcEntity userJdbcEntity){
        return new User(
                new UserId(userJdbcEntity.getId()),
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

    private UserJdbcEntity adaptToJdbcEntity(User user){

        return new UserJdbcEntity(
                user.getId().getValue(),
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
