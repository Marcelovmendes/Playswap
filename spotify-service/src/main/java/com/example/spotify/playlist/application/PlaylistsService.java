package com.example.spotify.playlist.application;

import com.example.spotify.playlist.domain.entity.Playlist;
import com.example.spotify.playlist.domain.entity.Track;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlaylistsService {
    List<Playlist> getListOfCurrentUsersPlaylistsAsync();
    List<Track> getPlaylistTracksAsync(String playlistId);
}
