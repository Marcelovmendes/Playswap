package com.example.spotify.common.infrastructure.repository;


import com.example.spotify.common.infrastructure.persistence.TracksJdbcEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackJdbcRepository extends CrudRepository<TracksJdbcEntity, UUID> {

    @Query("SELECT * FROM spotify.tracks WHERE platform = :platform")
    List<TracksJdbcEntity> findByPlatform(@Param("platform") String platform);

    @Query("""
           SELECT t.* FROM spotify.tracks t\s
           JOIN spotify.playlist_tracks pt ON t.id = pt.track_id
           WHERE pt.playlist_id = :playlistId
           ORDER BY pt.position
         \s""")
    List<TracksJdbcEntity> findByPlaylistId(@Param("playlistId") UUID playlistId);


}
