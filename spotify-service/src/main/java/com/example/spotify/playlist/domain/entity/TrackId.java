package com.example.spotify.playlist.domain.entity;

import reactor.util.annotation.NonNull;

import java.util.Objects;

public record TrackId(String value) {
    public TrackId {
        Objects.requireNonNull(value, "Track ID cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Track ID cannot be blank");
        }
    }

    @Override
    @NonNull
    public String toString() {
        return value;
    }
}
