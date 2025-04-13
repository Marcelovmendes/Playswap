package com.example.spotify.user.application;

import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.user.api.dto.UserProfileDTO;


public interface UserServicePort {
   UserProfileDTO getCurrentUserProfileAsync(UserToken accessToken);
}
