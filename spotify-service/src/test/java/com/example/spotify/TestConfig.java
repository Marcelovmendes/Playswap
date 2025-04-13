package com.example.spotify;


import com.example.spotify.user.domain.repository.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public UserRepository spotifyUserRepository() {

        return mock(UserRepository.class);
    }
}
