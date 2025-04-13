package com.example.spotify.playlist.api;

import com.example.spotify.common.infrastructure.adapter.SpotifyPlaylistAdapter;
import com.example.spotify.playlist.application.PlaylistsService;
import com.example.spotify.playlist.application.impl.AuthTokenProvider;
import com.example.spotify.playlist.application.TokenProvider;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/spotify/v1/playlist/")
public class PlaylistController {

    private static final Logger log = LoggerFactory.getLogger(PlaylistController.class);
    private final TokenProvider authToken;
    private final PlaylistsService playlistsService;

    public PlaylistController(AuthTokenProvider authToken, PlaylistsService playlistsService) {
        this.authToken = authToken;
        this.playlistsService = playlistsService;
    }

     @GetMapping("/")
    public ResponseEntity<Void> getPlaylists(HttpSession session) {
             log.info("Session request ID: {}", session.getId());
             String accessToken = (String) session.getAttribute("spotifyAccessToken");

        String token = authToken.getAccessToken();
         if (accessToken == null) {
             return ResponseEntity.status(401).build();
         }
         CompletableFuture<Paging<PlaylistSimplified>> playLists = playlistsService.getListOfCurrentUsersPlaylistsAsync(token);
         log.info("Playlists data: {}", playLists);
        return ResponseEntity.ok().build();
    }

}
