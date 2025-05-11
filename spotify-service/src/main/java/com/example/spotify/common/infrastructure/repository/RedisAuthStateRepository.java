package com.example.spotify.common.infrastructure.repository;

import com.example.spotify.auth.domain.entity.AuthState;
import com.example.spotify.auth.domain.repository.AuthStateRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class RedisAuthStateRepository implements AuthStateRepository {

    private static final String REDIS_AUTH_STATE = "oauth:state:";
    private final StringRedisTemplate redisTemplate;
    
    public RedisAuthStateRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @Override
    public void save(AuthState state, String codeVerifier, Duration timeout) {
        String key = REDIS_AUTH_STATE + state.getStateValue();
        redisTemplate.opsForValue().set(key, state.getCodeVerifier(), timeout);
    }
    @Override
    public Optional<AuthState> findCodeVerifier(AuthState state) {
        String key = REDIS_AUTH_STATE + state.getStateValue();
        String codeVerifier = redisTemplate.opsForValue().get(key);
        if ( codeVerifier == null ) return Optional.empty();

        return Optional.of(AuthState.create(state.getStateValue(), codeVerifier));
    }


    @Override
    public boolean exists(AuthState state) {
        if ( state == null || state.getStateValue().isEmpty()) return false;
        String key = REDIS_AUTH_STATE + state.getStateValue();
        Boolean exists = redisTemplate.hasKey(key);
        return exists;
    }
    @Override
    public void remove(AuthState state) {
        String key = REDIS_AUTH_STATE + state.getStateValue();
        redisTemplate.delete(key);
    }
}
