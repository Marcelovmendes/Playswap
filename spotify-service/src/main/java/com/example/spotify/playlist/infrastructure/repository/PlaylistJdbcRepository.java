package com.example.spotify.playlist.infrastructure.repository;

import com.example.spotify.playlist.infrastructure.persistence.PlayListJdbcEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaylistJdbcRepository extends CrudRepository<PlayListJdbcEntity, UUID> {
    @Query("""
            SELECT * FROM spotify.playlists 
            WHERE owner_id = :ownerId
            """)
    List<PlayListJdbcEntity> findByOwnerId(@Param("owner_id") UUID ownerId);

    @Query("""
           SELECT * FROM spotify.playlists\s
           WHERE name LIKE '%' || :name || '%'
          \s""")
    List<PlayListJdbcEntity> findByName(@Param("name") String name);

    @Query("""
           SELECT * FROM  spotify.playlists
           WHERE platform = :platform
           """)
    List<PlayListJdbcEntity> findByPlatform(@Param("platform") String platform);

}
