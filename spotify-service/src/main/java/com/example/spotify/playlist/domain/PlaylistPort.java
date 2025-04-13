package com.example.spotify.playlist.domain;

import com.example.spotify.auth.domain.service.UserToken;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.concurrent.CompletableFuture;

public interface PlaylistPort {
    Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(UserToken accessToken);
    CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylistsAsync(UserToken accessToken);
}
