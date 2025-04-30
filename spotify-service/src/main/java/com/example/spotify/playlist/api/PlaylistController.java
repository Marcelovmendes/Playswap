package com.example.spotify.playlist.api;

import com.example.spotify.playlist.application.PlaylistsService;
import com.example.spotify.playlist.application.impl.AuthTokenProvider;
import com.example.spotify.playlist.application.TokenProvider;
import com.example.spotify.playlist.domain.PlaylistPort;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/spotify/v1/playlist/")
public class PlaylistController {

    private static final Logger log = LoggerFactory.getLogger(PlaylistController.class);
    private final TokenProvider authToken;
    private final PlaylistsService playlistsService;
    private final PlaylistPort playlistPort;

    public PlaylistController(AuthTokenProvider authToken, PlaylistsService playlistsService, PlaylistPort playlistPort) {
        this.authToken = authToken;
        this.playlistsService = playlistsService;
        this.playlistPort = playlistPort;
    }

     @GetMapping("/")
    public ResponseEntity<CompletableFuture<Paging<PlaylistSimplified>>> getPlaylists(HttpSession session) {
             log.info("playlist request ID: {}", session.getId());
             String accessToken = (String) session.getAttribute("spotifyAccessToken");

        String token = authToken.getAccessToken();
         if (accessToken == null) {
             return ResponseEntity.status(401).build();
         }
         CompletableFuture<Paging<PlaylistSimplified>> playLists = playlistsService.getListOfCurrentUsersPlaylistsAsync(token);
         playLists.join();
        return ResponseEntity.ok(playLists);
    }

    @GetMapping("/tracks/")
    public ResponseEntity<Track[]> getTracks (HttpSession session) {
         log.info("tracks request ID: {}", session.getId());

        String token = authToken.getAccessToken();

        CompletableFuture<Track[]> tracks = playlistPort.getSeveralTracksAsync(token);

        tracks.join();

        return ResponseEntity.ok(tracks.join());

    }
    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<CompletableFuture<Paging<PlaylistTrack>>> getPlaylistTracks(@PathVariable String playlistId, HttpSession session) {
        log.info("playlist tracks request ID: {}", session.getId());

        String token = authToken.getAccessToken();

        CompletableFuture<Paging<PlaylistTrack>> playlistTracks = playlistsService.getPlaylistTracksAsync(token, playlistId);

        return ResponseEntity.ok(playlistTracks);
    }
}
