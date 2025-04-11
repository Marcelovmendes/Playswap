package com.example.spotify.user.application;

import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.user.api.dto.UserProfileDTO;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

public interface UserServiceContract {
   UserProfileDTO getCurrentUserProfileAsync(UserTokenService accessToken);
}
