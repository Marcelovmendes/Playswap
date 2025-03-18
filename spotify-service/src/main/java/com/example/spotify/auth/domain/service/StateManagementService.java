package com.example.spotify.auth.domain.service;

import java.time.Duration;

public interface StateManagementService {
    void saveState(String state, String codeVerifier, Duration timeout);
    boolean validateState(String state);
    String getCodeVerifier(String state);
    void removeState(String state);
}
