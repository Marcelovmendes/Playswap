package com.example.spotify.user.domain;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.user.domain.entity.UserEntity;


public interface UserProfilePort {
    se.michaelthelin.spotify.model_objects.specification.User getCurrentUsersProfileSync(UserToken userToken);
    UserEntity getCurrentUsersProfileAsync(UserToken token);
}
