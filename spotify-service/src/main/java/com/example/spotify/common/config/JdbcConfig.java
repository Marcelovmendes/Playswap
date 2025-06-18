package com.example.spotify.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableJdbcRepositories(basePackages = {
        "com.example.spotify.auth.infrastructure.repository",
        "com.example.spotify.playlist.infrastructure.repository",
        "com.example.spotify.user.infrastructure"
})
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {
}
