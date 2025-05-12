package com.example.spotify.playlist.application.impl;

import com.example.spotify.common.infrastructure.service.TokenProvider;
import com.example.spotify.playlist.application.PlaylistsService;
import com.example.spotify.playlist.domain.PlaylistPort;
import com.example.spotify.playlist.domain.entity.Playlist;
import com.example.spotify.playlist.domain.entity.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PlaylistServiceImpl implements PlaylistsService {

    private static final Logger log = LoggerFactory.getLogger(PlaylistServiceImpl.class);
    private final PlaylistPort playlist;
    private final TokenProvider tokenProvider;

    public PlaylistServiceImpl(PlaylistPort playlist, TokenProvider tokenProvider) {
        this.playlist = playlist;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public List<Playlist> getListOfCurrentUsersPlaylistsAsync() {
           String accessToken = tokenProvider.getAccessToken();
            List<Playlist> playlistsData = playlist.
                    getListOfCurrentUsersPlaylistsAsync(accessToken);
            log.info("Playlists data: {}", playlistsData.size());

            return playlistsData;
    }

    @Override
    public List<Track> getPlaylistTracksAsync( String playlistId) {
        List<Track> playlistTracks = playlist.getPlaylistTracksAsync(tokenProvider.getAccessToken(), playlistId);
        log.info("Playlist tracks data: {}", playlistTracks.size());
        return playlistTracks;
    }


}
