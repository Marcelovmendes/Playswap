package com.example.spotify.common.infrastructure.service;

public interface TokenProvider {
    String getAccessToken();
    boolean isTokenValid();
}
