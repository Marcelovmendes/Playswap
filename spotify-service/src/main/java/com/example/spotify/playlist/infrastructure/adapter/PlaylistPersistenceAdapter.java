package com.example.spotify.playlist.infrastructure.adapter;


import com.example.spotify.playlist.infrastructure.repository.PlaylistJdbcRepository;
import com.example.spotify.playlist.infrastructure.repository.PlaylistTrackJdbcRepository;
import com.example.spotify.playlist.infrastructure.repository.TrackJdbcRepository;
import com.example.spotify.playlist.domain.entity.*;
import com.example.spotify.playlist.domain.repository.PlaylistRepository;
import com.example.spotify.playlist.infrastructure.persistence.PlayListJdbcEntity;
import com.example.spotify.playlist.infrastructure.persistence.TracksJdbcEntity;
import com.example.spotify.user.domain.entity.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class PlaylistPersistenceAdapter implements PlaylistRepository {

    private static final Logger log = LoggerFactory.getLogger(PlaylistPersistenceAdapter.class);

    private final PlaylistJdbcRepository playlistJdbcRepository;
    private final TrackJdbcRepository trackJdbcRepository;
    private final PlaylistTrackJdbcRepository playlistTrackJdbcRepository;
    private final PlaylistCrudRepository playlistCrudRepository;


    public PlaylistPersistenceAdapter(PlaylistJdbcRepository playlistJdbcRepository,
                                      TrackJdbcRepository trackJdbcRepository,
                                      PlaylistTrackJdbcRepository playlistTrackJdbcRepository, PlaylistCrudRepository playlistCrudRepository) {
        this.playlistJdbcRepository = playlistJdbcRepository;
        this.trackJdbcRepository = trackJdbcRepository;
        this.playlistTrackJdbcRepository = playlistTrackJdbcRepository;
        this.playlistCrudRepository = playlistCrudRepository;
    }


    @Override
    public PlaylistAggregate findBySpotifyId(String playlistId) {
        Optional<PlayListJdbcEntity> entity = playlistJdbcRepository.findBySpotifyId(playlistId);
        if (entity.isEmpty()) {
            log.warn("Playlist with Spotify ID {} not found", playlistId);
            return null;
        }
        var playlistEntity = entity.get();

        log.info("Found playlist with Spotify ID {}: {}", playlistId, playlistEntity.getName());
        return mapToAggregate(playlistEntity);
    }

    @Override
    public List<PlaylistAggregate> findByUserId(UserId userId) {
        return List.of();
    }

    @Override
    public PlaylistAggregate save(PlaylistAggregate playlist) {
        Optional<PlayListJdbcEntity> existingEntity = playlistJdbcRepository.findBySpotifyId(
                playlist.getId().spotifyId()
        );
        PlayListJdbcEntity savedEntity;

        log.info("Creating new playlist: {}", playlist.getName());
        PlayListJdbcEntity entity = mapToPlaylistEntity(playlist);
        savedEntity = playlistCrudRepository.insert(entity);

        log.info("Saved playlist: {} - {}", savedEntity.getId(), savedEntity.getName());

        List<TracksJdbcEntity> trackEntities = saveAllTracks(playlist.getPlaylist().getTracks());
        log.info("Saved {} tracks for playlist: {}", trackEntities.size(), savedEntity.getName());

        return mapToAggregate(savedEntity);
    }

    @Override
    public List<TracksJdbcEntity> saveAllTracks(List<Track> playlist) {
        List<TracksJdbcEntity> trackEntities = playlist.stream()
                .map(track -> new TracksJdbcEntity(
                        track.getId().internalId(),
                        track.getName(),
                        track.getArtist(),
                        track.getAlbum(),
                        track.getDurationMs(),
                        track.getExternalUrl(),
                        track.getPreviewUrl(),
                        track.getImageUrl(),
                        "Spotify",
                        null,
                        null
                )).toList();

        return (List<TracksJdbcEntity>) trackJdbcRepository.saveAll(trackEntities);


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
                entity.getImage(),
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

        return new PlayListJdbcEntity(
                playlist.getId().internalId(),
                playlist.getId().spotifyId(),
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
    }

    private PlayListJdbcEntity updatePlaylistEntity(PlayListJdbcEntity existingEntity, PlaylistAggregate aggregate) {
        Playlist playlist = aggregate.getPlaylist();

        return PlayListJdbcEntity.reconstitute(
                existingEntity.getId(),
                playlist.getId().spotifyId(),
                playlist.getName(),
                playlist.getOwnerId().getInternalId(),
                playlist.getDescription(),
                playlist.isCollaborative(),
                playlist.isPublicAccess(),
                aggregate.getTrackCount(),
                playlist.getImageUrl(),
                playlist.getExternalUrl(),
                "spotify",
                false,
                "youtube",
                existingEntity.getCreatedAt(),
                LocalDateTime.now()
        );
    }


}