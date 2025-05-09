package com.example.spotify.user.domain.repository;

import com.example.spotify.user.domain.entity.UserEntity;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface UserRepository {
    Optional<UserEntity> findByEmail(String email);
    UserEntity save(UserEntity user) throws ExecutionException;
}
