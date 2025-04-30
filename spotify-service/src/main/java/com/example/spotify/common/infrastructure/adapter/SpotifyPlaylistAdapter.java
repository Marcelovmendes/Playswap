package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.common.exception.AuthenticationException;
import com.example.spotify.common.exception.ErrorType;
import com.example.spotify.common.exception.SpotifyApiException;
import org.apache.hc.core5.http.ParseException;
import com.example.spotify.playlist.domain.PlaylistPort;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class SpotifyPlaylistAdapter implements PlaylistPort {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SpotifyPlaylistAdapter.class);
    private final SpotifyApi spotifyApi;
    private static final String[] ids = new String[]{"2FDTHlrBguDzQkp7PVj16Q"};


    public SpotifyPlaylistAdapter(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }



    @Override
    public Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(String accessToken){
        try {
        spotifyApi.setAccessToken(accessToken);
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
    public CompletableFuture<Paging<PlaylistSimplified>> getListOfCurrentUsersPlaylistsAsync(String accessToken) {
        spotifyApi.setAccessToken(accessToken);
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();

        return request.executeAsync();


    }

    /***
     * retorna os albums/playlists
     * @param accessToken
     * @return
     */

    @Override
    public CompletableFuture<Track[]> getSeveralTracksAsync(String accessToken) {
        try {
            spotifyApi.setAccessToken(accessToken);


            CompletableFuture<Track[]> tracks = spotifyApi.getSeveralTracks(ids)
                    .build()
                    .executeAsync();
            tracks.join();
            return tracks;
        } catch (Exception e) {
            log.info("Error fetching tracks: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /***
     *  espera os ids das musicas
     * @param accessToken
     * @param playlistId
     * @return
     */

    @Override
    public CompletableFuture<Paging<PlaylistTrack>> getPlaylistTracksAsync(String accessToken, String playlistId) {
        spotifyApi.setAccessToken(accessToken);

        CompletableFuture<Paging<PlaylistTrack>> playlistTracks = spotifyApi.getPlaylistsItems(playlistId)
                .build()
                .executeAsync();
        playlistTracks.join();

        return playlistTracks;
    }
/***
 * espera o id da playlist e retorna as musicas do album
 *   items[]: added_at, added_by, is_local, tracks
 * tracks: album, name, id, explicit, type, uri, trackNumber, href
 *
 ***/


}
