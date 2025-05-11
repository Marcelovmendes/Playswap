package com.example.spotify.auth.domain.repository;

import com.example.spotify.auth.domain.entity.AuthState;

import java.time.Duration;
import java.util.Optional;

public interface AuthStateRepository {
    void save(AuthState state, Duration timeout);
    Optional<AuthState> findByStateValue(String stateValue);
    boolean exists(String stateValue);
    void remove(String stateValue);
}
