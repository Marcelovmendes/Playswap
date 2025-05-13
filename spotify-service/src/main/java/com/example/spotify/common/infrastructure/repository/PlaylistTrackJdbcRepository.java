package com.example.spotify.common.infrastructure.repository;

import com.example.spotify.common.infrastructure.persistence.PlaylistTrackJdbcEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistTrackJdbcRepository  extends CrudRepository<PlaylistTrackJdbcEntity, String> {

    @Query("""
            SELECT * FROM spotify.playlist_tracks
            WHERE playlist_id = :playlistId
            ORDER BY position
            """)
     List<PlaylistTrackJdbcEntity> findByPlaylistId(@Param("playlist_id") String playlistId);

}
