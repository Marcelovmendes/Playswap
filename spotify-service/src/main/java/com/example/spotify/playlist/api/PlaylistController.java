package com.example.spotify.playlist.api;

import com.example.spotify.playlist.application.PlaylistsService;
import com.example.spotify.playlist.domain.PlaylistPort;
import com.example.spotify.playlist.domain.entity.Playlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/spotify/v1/playlist/")
public class PlaylistController {

    private static final Logger log = LoggerFactory.getLogger(PlaylistController.class);
    private final PlaylistsService playlistsService;
    private final PlaylistPort playlistPort;

    public PlaylistController(PlaylistsService playlistsService, PlaylistPort playlistPort) {
        this.playlistsService = playlistsService;
        this.playlistPort = playlistPort;
    }

     @GetMapping("/")
    public ResponseEntity<List<Playlist>> getPlaylists() {
       List<Playlist> playLists = playlistsService.getListOfCurrentUsersPlaylistsAsync();

        return ResponseEntity.ok(playLists);
    }

    @GetMapping("/tracks")
    public ResponseEntity<Track[]> getTracks () {
        Track[] tracks = playlistPort.getSeveralTracksAsync();

        return ResponseEntity.ok(tracks);
    }
    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<HttpStatus> getPlaylistTracks(@PathVariable String playlistId) {
     playlistsService.getPlaylistTracksAsync(playlistId);
        return ResponseEntity.ok().build();
    }
}
