package com.example.spotify.user.domain;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.user.domain.entity.UserEntity;


public interface UserProfilePort {
    UserEntity getCurrentUsersProfileAsync(String token);
}
