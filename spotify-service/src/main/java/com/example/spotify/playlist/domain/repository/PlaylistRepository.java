package com.example.spotify.playlist.domain.repository;

import com.example.spotify.playlist.domain.entity.PlaylistAggregate;
import com.example.spotify.playlist.domain.entity.PlaylistId;
import com.example.spotify.user.domain.entity.UserId;

import java.util.List;

public interface PlaylistRepository {
    PlaylistAggregate findById(PlaylistId id);
    List<PlaylistAggregate> findByUserId(UserId userId);
    PlaylistAggregate save(PlaylistAggregate playlist);
    void delete(PlaylistId id);
    boolean existsById(PlaylistId id);

}
