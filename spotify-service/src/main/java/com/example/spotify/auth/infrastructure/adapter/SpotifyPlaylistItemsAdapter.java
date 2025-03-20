package com.example.spotify.auth.infrastructure.adapter;

import org.apache.hc.core5.http.ParseException;
import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.playlist.application.PlaylistItemsContract;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class SpotifyPlaylistItemsAdapter implements PlaylistItemsContract {

    private final SpotifyApi spotifyApi;

    public SpotifyPlaylistItemsAdapter(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(UserTokenService accessToken) throws IOException, ParseException, SpotifyWebApiException {

        spotifyApi.setAccessToken(accessToken.getAccessToken());
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();
        try {
            return request.execute();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }

    }

    @Override
    public CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylists_Async(UserTokenService accessToken) {
        spotifyApi.setAccessToken(accessToken.getAccessToken());
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();
        return request.executeAsync();


    }

}
