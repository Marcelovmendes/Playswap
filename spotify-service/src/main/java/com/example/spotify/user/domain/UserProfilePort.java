package com.example.spotify.user.domain;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.user.domain.entity.User;


public interface UserProfilePort {
    se.michaelthelin.spotify.model_objects.specification.User getCurrentUsersProfileSync(UserToken userToken);
    User getCurrentUsersProfileAsync(UserToken token);
}
