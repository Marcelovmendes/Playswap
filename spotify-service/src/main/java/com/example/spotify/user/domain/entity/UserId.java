package com.example.spotify.user.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private final UUID value;

    public UserId(UUID value) {
        this.value = value;
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId fromString(String id) {
        try {
            return new UserId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID de usuário inválido",e);
        }
    }

    public UUID getValue() {
        return value;
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
