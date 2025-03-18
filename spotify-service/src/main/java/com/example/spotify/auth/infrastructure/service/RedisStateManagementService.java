package com.example.spotify.auth.infrastructure.service;


import com.example.spotify.auth.domain.repository.AuthStateRepository;
import com.example.spotify.auth.domain.service.StateManagementService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisStateManagementService implements StateManagementService {

     private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(10);
     private final AuthStateRepository repository;

    public RedisStateManagementService(AuthStateRepository repository) {
        this.repository = repository;
    }
    @Override
    public void saveState(String state, String codeVerifier, Duration timeout) {
        repository.save(state, codeVerifier, timeout);
    }
    @Override
    public boolean validateState(String state) {
         if(state == null || state.isBlank()) return false;
        return repository.exists(state);
    }
    @Override
    public String getCodeVerifier(String state) {
        return repository.findCodeVerifier(state).orElse(null);
    }
    @Override
    public void removeState(String state) {
         repository.remove(state);
    }
}
