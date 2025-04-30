package com.example.spotify.playlist.application.impl;

import com.example.spotify.playlist.application.PlaylistsService;
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

    public PlaylistServiceImpl(PlaylistPort playlist) {
        this.playlist = playlist;
    }

    @Override
    public CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylistsAsync(String accessToken) {

            CompletableFuture<Paging<PlaylistSimplified>> playlistsData = playlist.
                    getListOfCurrentUsersPlaylistsAsync(accessToken);
             playlistsData.join();
            log.info("Playlists data: {}", playlistsData.join().getTotal());




            return playlistsData;
    }

    @Override
    public CompletableFuture<Paging<PlaylistTrack>> getPlaylistTracksAsync(String accessToken, String playlistId) {
        CompletableFuture<Paging<PlaylistTrack>> playlistTracks = playlist.getPlaylistTracksAsync(accessToken, playlistId);
        playlistTracks.join();
        log.info("Playlist tracks data: {}", playlistTracks.join().getTotal());
        return playlistTracks;
    }
}
