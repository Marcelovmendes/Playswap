package com.example.spotify.user.domain.entity;

import java.util.UUID;

public record UserId(UUID value) {

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId fromString(String id) {
        try {
            return new UserId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID de usuário inválido", e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
