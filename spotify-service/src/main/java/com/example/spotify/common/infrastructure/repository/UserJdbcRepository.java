package com.example.spotify.common.infrastructure.repository;

import com.example.spotify.common.infrastructure.persistence.UserJdbcEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJdbcRepository extends CrudRepository<UserJdbcEntity, UUID> {

    @Query("SELECT * FROM spotify.users WHERE email = :email")
    Optional<UserJdbcEntity> findByEmail(@Param("email") String email);


    @Query(""" 
            INSERT INTO spotify.users (id, birth_date, country, display_name, email, external_urls, followers_count, hrf,
            photo_cover, spotify_uri, type, registered_user_id, created_at, updated_at)
             VALUES
            (:#{#user.id}, :#{#user.birthdate}, :#{#user.country}, :#{#user.displayName}, :#{#user.email},
            :#{#user.externalUrls}, :#{#user.followersCount}, :#{#user.href}, :#{#user.photoCover},
            :#{#user.spotifyUri}, :#{#user.type}, :#{#user.userRegisteredId},
             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING *
""")
    UserJdbcEntity save(@Param("user") UserJdbcEntity user);
}



