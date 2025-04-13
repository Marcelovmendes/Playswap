package com.example.spotify.playlist.api;


import com.example.spotify.auth.domain.service.TokenStoragePort;
import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.user.api.dto.UserProfileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/playlist/")
public class PlaylistController {

    private final TokenStoragePort tokenStorage;

    public PlaylistController(TokenStoragePort tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    public ResponseEntity<?> getPlaylistsByUser() {

        UserToken token = tokenStorage.retrieveUserToken();


        return ResponseEntity.ok("ok");
    }


}
