package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.common.exception.SpotifyApiExceptionTranslator;
import com.example.spotify.playlist.domain.PlaylistPort;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

@Component
public class SpotifyPlaylistAdapter extends ExternalServiceAdapter implements PlaylistPort {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SpotifyPlaylistAdapter.class);
    private static final String[] ids = new String[]{"7sTyAjxDXq9afwfSQy6D0s"};


    public SpotifyPlaylistAdapter(SpotifyApi spotifyApi, SpotifyApiExceptionTranslator exceptionTranslator) {
        super(spotifyApi, exceptionTranslator);
    }
    @Override
    public Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsSync(String accessToken){

        spotifyApi.setAccessToken(accessToken);
        GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();

            return executeSync(request::execute,
                    "fetching user playlists"
            );


    }

    @Override
    public Paging<PlaylistSimplified> getListOfCurrentUsersPlaylistsAsync(String accessToken) {
        spotifyApi.setAccessToken(accessToken);

        return executeAsync(
                spotifyApi.getListOfCurrentUsersPlaylists()
                        .limit(10)
                        .build()
                        .executeAsync(),
                "fetching user playlists"
        );

    }

    @Override
    public Track[] getSeveralTracksAsync(String accessToken) {
       spotifyApi.setAccessToken(accessToken);

            return executeAsync(
                    spotifyApi.getSeveralTracks(ids)
                            .build()
                            .executeAsync(),
                    "fetching tracks"
            );


    }

    @Override
    public Paging<PlaylistTrack> getPlaylistTracksAsync(String accessToken, String playlistId) {
        spotifyApi.setAccessToken(accessToken);
       return  executeAsync(
               spotifyApi.getPlaylistsItems(playlistId)
                       .build()
                       .executeAsync(),
                "fetching playlist tracks"
       );

    }

/***
 * espera o id da playlist e retorna as musicas do album
 *   items[]: added_at, added_by, is_local, tracks
 * tracks: album, name, id, explicit, type, uri, trackNumber, href
 *
 ***/


}
