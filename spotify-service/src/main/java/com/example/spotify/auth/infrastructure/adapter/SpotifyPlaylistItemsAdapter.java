package com.example.spotify.auth.infrastructure.adapter;

import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ExceptionType;
import com.example.spotify.common.exception.SpotifyApiException;
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
        try {
        spotifyApi.setAccessToken(accessToken.getAccessToken());
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();

            return request.execute();

        } catch (IOException e) {
            throw new SpotifyApiException("Failed to connect to Spotify API",
                    ExceptionType.SPOTIFY_API_EXCEPTION);
        } catch (SpotifyWebApiException e) {
            if (e.getMessage().contains("401")) {
                throw new AuthenticationException("Invalid token",
                        ExceptionType.TOKEN_EXPIRED);
            }
            throw new SpotifyApiException("Spotify API error",
                    ExceptionType.SPOTIFY_API_EXCEPTION);
        } catch (ParseException e) {
            throw new SpotifyApiException("Error parsing response from Spotify API",
                    ExceptionType.SPOTIFY_API_EXCEPTION);
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
