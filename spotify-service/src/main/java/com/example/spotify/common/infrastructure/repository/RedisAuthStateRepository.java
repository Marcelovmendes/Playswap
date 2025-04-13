package com.example.spotify.common.infrastructure.repository;

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
    public void save(String state, String codeVerifier, Duration timeout) {
        String key = REDIS_AUTH_STATE + state;
        redisTemplate.opsForValue().set(key, codeVerifier, timeout);
    }
    @Override
    public Optional<String> findCodeVerifier(String state) {
        String key = REDIS_AUTH_STATE + state;
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public boolean exists(String state) {
        if ( state == null || state.isEmpty()) return false;
        String key = REDIS_AUTH_STATE + state;
        Boolean exists = redisTemplate.hasKey(key);
        return Boolean.TRUE.equals(exists);
    }

    @Override
    public void remove(String state) {
        String key = REDIS_AUTH_STATE + state;
        redisTemplate.delete(key);
    }
}
