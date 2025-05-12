package com.example.spotify.playlist.application;

import com.example.spotify.playlist.domain.entity.Playlist;
import com.example.spotify.playlist.domain.entity.Track;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlaylistsService {
    List<Playlist> getListOfCurrentUsersPlaylistsAsync();
    List<Track> getPlaylistTracksAsync(String playlistId);
}
