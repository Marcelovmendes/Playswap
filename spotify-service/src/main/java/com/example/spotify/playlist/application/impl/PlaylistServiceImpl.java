package com.example.spotify.playlist.application.impl;

import com.example.spotify.playlist.application.PlaylistsService;
import com.example.spotify.playlist.application.TokenProvider;
import com.example.spotify.playlist.domain.PlaylistPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

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
    public Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsAsync() {

            Paging<PlaylistSimplified> playlistsData = playlist.
                    getListOfCurrentUsersPlaylistsAsync(tokenProvider.getAccessToken());
            log.info("Playlists data: {}", playlistsData.getTotal());

            return playlistsData;
    }

    @Override
    public Paging<PlaylistTrack> getPlaylistTracksAsync(String accessToken, String playlistId) {
        Paging<PlaylistTrack> playlistTracks = playlist.getPlaylistTracksAsync(accessToken, playlistId);
        log.info("Playlist tracks data: {}", playlistTracks.getTotal());
        return playlistTracks;
    }


}
