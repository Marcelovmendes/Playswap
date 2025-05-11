package com.example.spotify.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableJdbcRepositories(basePackages = {
        "com.example.spotify.common.infrastructure.repository"
})
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {
}
