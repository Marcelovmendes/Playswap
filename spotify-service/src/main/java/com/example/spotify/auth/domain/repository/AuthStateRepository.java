package com.example.spotify.auth.domain.repository;

import java.time.Duration;
import java.util.Optional;

public interface AuthStateRepository {
    void save (String state, String codeVerifier, Duration timeout);
    Optional <String> findCodeVerifier (String state);
    boolean exists (String state);
    void remove (String state);
}
