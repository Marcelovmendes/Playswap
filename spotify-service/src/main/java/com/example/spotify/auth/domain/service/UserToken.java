package com.example.spotify.auth.domain.service;

import java.time.Instant;

public interface UserToken {
    String getAccessToken();
    String getRefreshToken();
    Instant getExpiresAt();
}
