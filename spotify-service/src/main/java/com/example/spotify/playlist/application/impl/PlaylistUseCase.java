package com.example.spotify.playlist.application.impl;

import com.example.spotify.playlist.domain.entity.PlaylistId;
import com.example.spotify.user.domain.entity.UserId;

public interface PlaylistUseCase {
     PlaylistId createPlaylist(String name, UserId userId, boolean collaborative, boolean publicAccess);
     boolean requestPlayListConversion(PlaylistId playlistId, String targetPlatform);
}
