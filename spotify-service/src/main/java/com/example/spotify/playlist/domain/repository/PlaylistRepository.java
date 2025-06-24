package com.example.spotify.playlist.domain.repository;

import com.example.spotify.playlist.domain.entity.PlaylistAggregate;
import com.example.spotify.playlist.domain.entity.PlaylistId;
import com.example.spotify.playlist.domain.entity.Track;
import com.example.spotify.playlist.infrastructure.persistence.TracksJdbcEntity;
import com.example.spotify.user.domain.entity.UserId;

import java.util.List;

public interface PlaylistRepository {
    PlaylistAggregate findBySpotifyId(String id);
    List<PlaylistAggregate> findByUserId(UserId userId);
    PlaylistAggregate save(PlaylistAggregate playlist);
    List<TracksJdbcEntity> saveAllTracks(List<Track> tracks);
    void delete(PlaylistId id);
    boolean existsById(PlaylistId id);

}
