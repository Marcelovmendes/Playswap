package com.example.spotify.playlist.domain;

import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.concurrent.CompletableFuture;

public interface PlaylistPort {
    Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(String accessToken);
    CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylistsAsync(String accessToken);
    CompletableFuture<Track[]> getSeveralTracksAsync(String accessToken);
    CompletableFuture<Paging<PlaylistTrack>> getPlaylistTracksAsync(String accessToken, String playlistId);
}
