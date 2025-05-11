package com.example.spotify.playlist.domain;

import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.concurrent.CompletableFuture;

public interface PlaylistPort {
    Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync( String accessToken);
    Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsAsync( String accessToken);
    Track[] getSeveralTracksAsync();
    Paging<PlaylistTrack> getPlaylistTracksAsync(String accessToken ,String playlistId);
}
