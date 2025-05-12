package com.example.spotify.playlist.domain.entity;

import reactor.util.annotation.NonNull;

import java.util.Objects;

public record PlaylistId(String value) {
    public PlaylistId {
        Objects.requireNonNull(value, "Playlist ID cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Playlist ID cannot be blank");
        }
    }

    @Override
    @NonNull
    public String toString() {
        return value;
    }
}
