package com.example.spotify.playlist.domain.entity;

import reactor.util.annotation.NonNull;

public record PlaylistId(String value) {
    public PlaylistId {

        if (value == null) {
            throw new IllegalArgumentException("Playlist ID cannot be blank");
        }
    }
    @Override
    @NonNull
    public String toString() {
        return value;
    }
}
