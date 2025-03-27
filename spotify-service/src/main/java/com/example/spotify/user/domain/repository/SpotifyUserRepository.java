package com.example.spotify.user.domain.repository;

import com.example.spotify.user.domain.entity.SpotifyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpotifyUserRepository extends CrudRepository<SpotifyUser, String> {
    Optional<SpotifyUser> findByEmail(String email);
}
