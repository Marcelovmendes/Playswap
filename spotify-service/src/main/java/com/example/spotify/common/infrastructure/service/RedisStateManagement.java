package com.example.spotify.common.infrastructure.service;


import com.example.spotify.auth.domain.repository.AuthStateRepository;
import com.example.spotify.auth.domain.service.StateManagementPort;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisStateManagement implements StateManagementPort {

     private final AuthStateRepository repository;

    public RedisStateManagement(AuthStateRepository repository) {
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
