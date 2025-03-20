package com.example.spotify.auth.domain.service;

import java.time.Instant;

public interface UserTokenService {
    String getAccessToken();
    String getRefreshToken();
    Instant getExpiresAt();
    boolean isExpired();
}
