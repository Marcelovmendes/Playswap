package com.example.spotify.playlist.application;

import com.example.spotify.auth.domain.service.UserTokenService;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface PlaylistItemsContract {
    Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(UserTokenService accessToken) throws IOException, ParseException, SpotifyWebApiException;
    CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylists_Async(UserTokenService accessToken);
}
