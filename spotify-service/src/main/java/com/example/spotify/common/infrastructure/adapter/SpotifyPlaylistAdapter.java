package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import com.example.spotify.common.exception.SpotifyApiException;
import org.apache.hc.core5.http.ParseException;
import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.playlist.domain.PlaylistPort;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class SpotifyPlaylistAdapter implements PlaylistPort {

    private final SpotifyApi spotifyApi;

    public SpotifyPlaylistAdapter(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(UserToken accessToken){
        try {
        spotifyApi.setAccessToken(accessToken.getAccessToken());
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();

            return request.execute();

        } catch (IOException e) {
            throw new SpotifyApiException("Failed to connect to Spotify API",
                    ErrorType.SPOTIFY_API_EXCEPTION);
        } catch (SpotifyWebApiException e) {
            if (e.getMessage().contains("401")) {
                throw new AuthenticationException("Invalid token",
                        ErrorType.TOKEN_EXPIRED);
            }
            throw new SpotifyApiException("Spotify API error",
                    ErrorType.SPOTIFY_API_EXCEPTION);
        } catch (ParseException e) {
            throw new SpotifyApiException("Error parsing response from Spotify API",
                    ErrorType.SPOTIFY_API_EXCEPTION);
        }

    }

    @Override
    public CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylistsAsync(UserToken accessToken) {
        spotifyApi.setAccessToken(accessToken.getAccessToken());
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();
        return request.executeAsync();


    }

}
