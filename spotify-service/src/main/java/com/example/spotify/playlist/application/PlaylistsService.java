package com.example.spotify.playlist.application;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.util.concurrent.CompletableFuture;

public interface PlaylistsService {
    Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsAsync(String accessToken);
    Paging<PlaylistTrack> getPlaylistTracksAsync(String accessToken, String playlistId);
}
