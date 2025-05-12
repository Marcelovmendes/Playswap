package com.example.spotify.playlist.domain;


import com.example.spotify.playlist.domain.entity.Playlist;
import com.example.spotify.playlist.domain.entity.Track;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlaylistPort {
    Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(String accessToken);
    List<Playlist> getListOfCurrentUsersPlaylistsAsync(String accessToken);
    Track[] getSeveralTracksAsync();
    List<Track> getPlaylistTracksAsync(String accessToken , String playlistId);
}
