package com.example.spotify.playlist.domain.entity;

import reactor.util.annotation.NonNull;

import java.util.UUID;

public record PlaylistId(String value, UUID internalId) {
    public static PlaylistId fromSpotifyId(String spotifyId) {
        if (spotifyId == null || spotifyId.isBlank()) {
            throw new IllegalArgumentException("Spotify Playlist ID cannot be blank");
        }
        UUID internalId = UUID.nameUUIDFromBytes(("spotify:" + spotifyId).getBytes());
        return new PlaylistId(spotifyId, internalId);
    }
    public static PlaylistId fromInternalId(UUID internalId) {
        if (internalId == null) {
            throw new IllegalArgumentException("Internal ID cannot be null");
        }
        return new PlaylistId(internalId.toString(), internalId);
    }
    public static PlaylistId reconstitute(String spotifyId, UUID internalId) {
        return new PlaylistId(spotifyId, internalId);
    }

    public String spotifyId() {
        return value;
    }

    @Override
    @NonNull
    public String toString() {
        return value;
    }

    public UUID getInternalId() {
        return internalId;
    }

}
