package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.common.exception.SpotifyApiExceptionTranslator;
import com.example.spotify.playlist.domain.PlaylistPort;
import com.example.spotify.playlist.domain.entity.Playlist;
import com.example.spotify.playlist.domain.entity.PlaylistId;
import com.example.spotify.playlist.domain.entity.Track;
import com.example.spotify.playlist.domain.entity.TrackId;
import com.example.spotify.user.domain.entity.UserId;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.util.ArrayList;
import java.util.List;

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
    public List<Playlist> getListOfCurrentUsersPlaylistsAsync(String accessToken) {
        spotifyApi.setAccessToken(accessToken);

        Paging<PlaylistSimplified> spotifyPlaylists = executeAsync(
                spotifyApi.getListOfCurrentUsersPlaylists()
                        .limit(10)
                        .build()
                        .executeAsync(),
                "fetching user spotifyPlaylists"
        );
        List<Playlist> playlists = new ArrayList<>();
        for ( PlaylistSimplified playlist : spotifyPlaylists.getItems()) {
            log.info("Playlist name: {}, ID: {}", playlist.getName(), playlist.getId());
            playlists.add(convertToPlaylist(playlist));
        }
        return playlists;

    }

    @Override
    public com.example.spotify.playlist.domain.entity.Track[] getSeveralTracksAsync() {
        return new Track[0];
    }

    /***
    @Override
    public Track[] getSeveralTracksAsync() {
       spotifyApi.setAccessToken(accessToken);

            return executeAsync(
                    spotifyApi.getSeveralTracks(ids)
                            .build()
                            .executeAsync(),
                    "fetching tracks"
            );


    }
***/
    @Override
    public List<Track> getPlaylistTracksAsync(String accessToken, String playlistId) {
        spotifyApi.setAccessToken(accessToken);
        Paging<PlaylistTrack> spotifyTracks =  executeAsync(
               spotifyApi.getPlaylistsItems(playlistId)
                       .build()
                       .executeAsync(),
                "fetching playlist tracks"
       );

        return convertPlaylistTracks(spotifyTracks);

    }
    private Playlist convertToPlaylist(PlaylistSimplified spotifyPlaylist) {
        String imageUrl = null;
        if (spotifyPlaylist.getImages() != null && spotifyPlaylist.getImages().length > 0) {
            imageUrl = spotifyPlaylist.getImages()[0].getUrl();
        }

        return new Playlist(
                new PlaylistId(spotifyPlaylist.getId()),
                spotifyPlaylist.getName(),
                UserId.fromString(spotifyPlaylist.getOwner().getId()),
                "spotifyPlaylist",
                spotifyPlaylist.getIsCollaborative(),
                spotifyPlaylist.getIsPublicAccess(),
                spotifyPlaylist.getTracks().getTotal(),
                imageUrl,
                List.of(),
                spotifyPlaylist.getExternalUrls().get("spotify")
        );
    }
    private List<Track> convertPlaylistTracks(Paging<PlaylistTrack> playlistTracks) {
        List<Track> tracks = new ArrayList<>();

        for (PlaylistTrack playlistTrack : playlistTracks.getItems()) {
            if (playlistTrack.getTrack() instanceof se.michaelthelin.spotify.model_objects.specification.Track) {
                tracks.add(convertTrack((se.michaelthelin.spotify.model_objects.specification.Track) playlistTrack.getTrack()));
            }
        }

        return tracks;
    }
    private Track convertTrack(se.michaelthelin.spotify.model_objects.specification.Track spotifyTrack) {
        String imageUrl = null;
        if (spotifyTrack.getAlbum() != null && spotifyTrack.getAlbum().getImages() != null
                && spotifyTrack.getAlbum().getImages().length > 0) {
            imageUrl = spotifyTrack.getAlbum().getImages()[0].getUrl();
        }

        String artist = "";
        if (spotifyTrack.getArtists() != null && spotifyTrack.getArtists().length > 0) {
            artist = spotifyTrack.getArtists()[0].getName();
        }

        String album = spotifyTrack.getAlbum() != null ? spotifyTrack.getAlbum().getName() : "";

        return new Track(
                new TrackId(spotifyTrack.getId()),
                spotifyTrack.getName(),
                artist,
                album,
                spotifyTrack.getDurationMs(),
                spotifyTrack.getExternalUrls().get("spotify"),
                spotifyTrack.getPreviewUrl(),
                imageUrl
        );
    }
    private Playlist convertToPlaylistWithTracks(se.michaelthelin.spotify.model_objects.specification.Playlist spotifyPlaylist) {
        String imageUrl = null;
        if (spotifyPlaylist.getImages() != null && spotifyPlaylist.getImages().length > 0) {
            imageUrl = spotifyPlaylist.getImages()[0].getUrl();
        }

        List<Track> tracks = new ArrayList<>();
        if (spotifyPlaylist.getTracks() != null && spotifyPlaylist.getTracks().getItems() != null) {
            for (PlaylistTrack playlistTrack : spotifyPlaylist.getTracks().getItems()) {
                if (playlistTrack.getTrack() instanceof se.michaelthelin.spotify.model_objects.specification.Track) {
                    tracks.add(convertTrack((se.michaelthelin.spotify.model_objects.specification.Track) playlistTrack.getTrack()));
                }
            }
        }

        return new Playlist(
                new PlaylistId(spotifyPlaylist.getId()),
                spotifyPlaylist.getName(),
                UserId.fromString(spotifyPlaylist.getOwner().getId()),
                spotifyPlaylist.getDescription(),
                spotifyPlaylist.getIsCollaborative(),
                spotifyPlaylist.getIsPublicAccess(),
                spotifyPlaylist.getTracks().getTotal(),
                imageUrl,
                tracks,
                spotifyPlaylist.getExternalUrls().get("spotify")
        );
    }



}
