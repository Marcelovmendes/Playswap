package com.example.spotify.playlist.domain.repository;

import com.example.spotify.playlist.domain.entity.PlaylistId;
import com.example.spotify.playlist.domain.entity.PlaylistSummary;
import com.example.spotify.playlist.domain.entity.Track;

import java.util.List;

public interface PlaylistQuery {
    List<PlaylistSummary> getPlaylistSummaries(String userId);
    PlaylistSummary getPlaylistSummary(PlaylistId playlistId);
    List<Track> getPlaylistTracks(PlaylistId playlistId);
}
