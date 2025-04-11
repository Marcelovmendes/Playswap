package com.example.spotify.user.domain.repository;

import com.example.spotify.user.domain.entity.SpotifyUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SpotifyUserRepository extends CrudRepository<SpotifyUser, String> {

    @Query("SELECT * FROM spotify.users WHERE email = :email")
    Optional<SpotifyUser> findByEmail(@Param("email") String email);

    SpotifyUser save(SpotifyUser user);
}



