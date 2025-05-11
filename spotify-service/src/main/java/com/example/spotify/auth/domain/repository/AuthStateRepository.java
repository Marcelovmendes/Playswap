package com.example.spotify.auth.domain.repository;

import com.example.spotify.auth.domain.entity.AuthState;

import java.time.Duration;
import java.util.Optional;

public interface AuthStateRepository {
    void save (AuthState state, String codeVerifier, Duration timeout);
    Optional <AuthState> findCodeVerifier (AuthState state);
    boolean exists (AuthState state);
    void remove (AuthState state);
}
