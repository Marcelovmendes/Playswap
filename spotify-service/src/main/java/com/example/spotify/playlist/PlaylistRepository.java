package com.example.spotify.playlist;

import org.springframework.data.repository.CrudRepository;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
    List<Playlist> getUserPlaylists(String accessToken, int limit, int offset);
    Playlist getPlaylistDetails(String accessToken, String playlistId);
}
