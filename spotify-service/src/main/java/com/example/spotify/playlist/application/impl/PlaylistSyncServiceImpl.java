package com.example.spotify.playlist.application.impl;

import com.example.spotify.playlist.infrastructure.persistence.TracksJdbcEntity;
import com.example.spotify.playlist.infrastructure.repository.TrackJdbcRepository;
import com.example.spotify.auth.infrastructure.TokenProvider;
import com.example.spotify.playlist.application.PlaylistsService;
import com.example.spotify.playlist.domain.PlaylistPort;
import com.example.spotify.playlist.domain.entity.Playlist;
import com.example.spotify.playlist.domain.entity.PlaylistAggregate;
import com.example.spotify.playlist.domain.entity.Track;
import com.example.spotify.playlist.domain.repository.PlaylistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistSyncServiceImpl implements PlaylistsService {

    private static final Logger log = LoggerFactory.getLogger(PlaylistSyncServiceImpl.class);
    private final PlaylistPort playlistPort;
    private final TokenProvider tokenProvider;
    private final PlaylistRepository playlistRepository;
    private final TrackJdbcRepository trackJdbcRepository;


    public PlaylistSyncServiceImpl(PlaylistPort playlist, TokenProvider tokenProvider, PlaylistRepository playlistRepository, TrackJdbcRepository trackJdbcRepository) {
        this.playlistPort = playlist;
        this.tokenProvider = tokenProvider;
        this.playlistRepository = playlistRepository;
        this.trackJdbcRepository = trackJdbcRepository;
    }

    @Override
    public List<Playlist> getListOfCurrentUsersPlaylistsAsync() {
           String accessToken = tokenProvider.getAccessToken();
            List<Playlist> playlistsData = playlistPort.
                    getListOfCurrentUsersPlaylistsAsync(accessToken);

            log.info("Playlists data: {}", playlistsData.size());
            for (Playlist playlist : playlistsData) {
                PlaylistAggregate aggregate = PlaylistAggregate.create(
                        playlist.getId(),
                        playlist.getName(),
                        playlist.getOwnerId(),
                        playlist.getOwnerName(),
                        playlist.getDescription(),
                        playlist.isCollaborative(),
                        playlist.isPublicAccess(),
                        playlist.getTrackCount(),
                        playlist.getImageUrl(),
                        new ArrayList<>(),
                        playlist.getExternalUrl()
                );
                playlistRepository.save(aggregate);
                log.info("Saved playlist: {} - {} ", playlist.getId(), playlist.getName());
            }

            return playlistsData;
    }

    @Override
    public void getPlaylistTracksAsync( String playlistId) {

        PlaylistAggregate aggregate = playlistRepository.findById(playlistId);
        if (aggregate == null) {
            throw new IllegalArgumentException("Playlist not found: " + playlistId);
        }
        String accessToken = tokenProvider.getAccessToken();
        List<Track> spotifyTracks = playlistPort.getPlaylistTracksAsync(playlistId, accessToken);

        List<TracksJdbcEntity> fromEntity = playlistRepository.saveAllTracks(spotifyTracks);
        trackJdbcRepository.saveAll(fromEntity);

        for (Track track : spotifyTracks) {
            aggregate.addTrack(track);
        }
        playlistRepository.save(aggregate);
        log.info("Synced {} tracks for playlist {}",fromEntity, playlistId);

    };


}
