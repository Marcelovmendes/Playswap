package com.example.spotify.playlist.application;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.concurrent.CompletableFuture;

public interface PlaylistsService {
    CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylistsAsync(String accessToken);
}
