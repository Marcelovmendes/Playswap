package com.example.spotify.common.infrastructure.persistence;

import com.example.spotify.common.infrastructure.repository.UserJdbcRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class UserPersistence  {

    private final UserJdbcRepository userRepository;
    public UserPersistence(UserJdbcRepository userRepository) {
        this.userRepository = userRepository;
    }



}
