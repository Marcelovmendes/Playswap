package com.example.spotify.playlist.application.impl;

import com.example.spotify.playlist.application.PlaylistsService;
import com.example.spotify.playlist.domain.PlaylistPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

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

        if (accessToken != null && !accessToken.isEmpty()) {
            CompletableFuture<Paging<PlaylistSimplified>> playlistsData = playlist.
                    getListOfCurrentUsersPlaylistsAsync(accessToken);
             playlistsData.join();
            log.info("Playlists data: {}", playlistsData);
            return playlistsData;

        }
        return null;
    }
}
