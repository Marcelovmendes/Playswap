package com.example.spotify.common.infrastructure.adapter;


import com.example.spotify.common.infrastructure.persistence.PlayListJdbcEntity;
import com.example.spotify.common.infrastructure.persistence.TracksJdbcEntity;
import com.example.spotify.common.infrastructure.repository.PlaylistJdbcRepository;
import com.example.spotify.common.infrastructure.repository.PlaylistTrackJdbcRepository;
import com.example.spotify.common.infrastructure.repository.TrackJdbcRepository;
import com.example.spotify.playlist.domain.entity.*;
import com.example.spotify.playlist.domain.repository.PlaylistRepository;
import com.example.spotify.user.domain.entity.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaylistPersistenceAdapter implements PlaylistRepository {

    private static final Logger log = LoggerFactory.getLogger(PlaylistPersistenceAdapter.class);

    private final PlaylistJdbcRepository playlistJdbcRepository;
    private final TrackJdbcRepository trackJdbcRepository;
    private final PlaylistTrackJdbcRepository playlistTrackJdbcRepository;

    public PlaylistPersistenceAdapter(PlaylistJdbcRepository playlistJdbcRepository, TrackJdbcRepository trackJdbcRepository,
                                      PlaylistTrackJdbcRepository playlistTrackJdbcRepository) {
        this.playlistJdbcRepository = playlistJdbcRepository;
        this.trackJdbcRepository = trackJdbcRepository;
        this.playlistTrackJdbcRepository = playlistTrackJdbcRepository;
    }


    @Override
    public PlaylistAggregate findById(PlaylistId id) {
        return null;
    }

    @Override
    public List<PlaylistAggregate> findByUserId(UserId userId) {
        return List.of();
    }

    @Override
    public PlaylistAggregate save(PlaylistAggregate playlist) {
        return null;
    }

    @Override
    public void delete(PlaylistId id) {

    }

    @Override
    public boolean existsById(PlaylistId id) {
        return false;
    }
    private PlaylistAggregate mapToAggregate(PlayListJdbcEntity entity) {

        List <TracksJdbcEntity> trackEntities = trackJdbcRepository.findByPlaylistId(entity.getId());
        List<Track> tracks = trackEntities.stream()
                .map(this::mapToTrack)
                .toList();
        Playlist playlist = new Playlist(
                PlaylistId.fromInternalId(entity.getId()),
                entity.getName(),
               UserId.fromInternalId(entity.getOwnerId()),
                entity.getDescription(),
                entity.getDescription(),
                entity.isCollaborative(),
                entity.isPublicAccess(),
                entity.getTrackCount(),
                entity.getImageUrl(),
                tracks,
                entity.getExternalUrl()
        );
        return PlaylistAggregate.reconstitute(playlist, tracks);
    }
    private Track mapToTrack(TracksJdbcEntity entity) {
        return new Track(
               TrackId.fromInternalId(entity.getId()),
                entity.getName(),
                entity.getArtist(),
                entity.getAlbum(),
                entity.getDurationMs(),
                entity.getExternalUrl(),
                entity.getPreviewUrl(),
                entity.getImageUrl()
        );
    }
    private PlayListJdbcEntity mapToPlaylistEntity(PlaylistAggregate aggregate) {
        Playlist playlist = aggregate.getPlaylist();

        PlayListJdbcEntity entity = new PlayListJdbcEntity(
                playlist.getId().getInternalId(),
                playlist.getName(),
                playlist.getOwnerId().getInternalId(),
                playlist.getDescription(),
                playlist.isCollaborative(),
                playlist.isPublicAccess(),
                aggregate.getTrackCount(),
                playlist.getImageUrl(),
                playlist.getExternalUrl(),
                "spotify",
                aggregate.isEmpty(),
                "youtube",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return entity;
    }


}