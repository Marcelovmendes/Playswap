package com.example.spotify.user.domain.repository;

import com.example.spotify.user.domain.entity.User;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user) throws ExecutionException;
}
