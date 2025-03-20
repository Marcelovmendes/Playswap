package com.example.spotify.playlist;

import se.michaelthelin.spotify.model_objects.specification.Playlist;

import java.util.List;

public interface PlaylistRepository {
    List<Playlist> getUserPlaylists(String accessToken, int limit, int offset);
    Playlist getPlaylistDetails(String accessToken, String playlistId);
}
