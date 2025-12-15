package com.example.spotify.playlist.application.impl;

import com.example.spotify.auth.infrastructure.TokenProvider;
import com.example.spotify.playlist.domain.PlaylistPort;
import com.example.spotify.playlist.domain.entity.Playlist;
import com.example.spotify.playlist.domain.entity.Track;
import com.example.spotify.playlist.domain.entity.TrackId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PlaylistSyncServiceImpl Unit Tests")
class PlaylistSyncServiceImplTest {

    @Mock
    private PlaylistPort playlistPort;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private PlaylistSyncServiceImpl service;

    private static final String ACCESS_TOKEN = "test-access-token";
    private static final String PLAYLIST_ID = "playlist123";

    @Test
    @DisplayName("getListOfCurrentUsersPlaylistsAsync should successfully return playlists")
    void getListOfCurrentUsersPlaylistsAsync_Success() {
        List<Playlist> expectedPlaylists = List.of(
                createPlaylist("1", "My Playlist 1"),
                createPlaylist("2", "My Playlist 2")
        );

        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getListOfCurrentUsersPlaylistsAsync(ACCESS_TOKEN)).thenReturn(expectedPlaylists);

        List<Playlist> result = service.getListOfCurrentUsersPlaylistsAsync();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("My Playlist 1");
        assertThat(result.get(1).getName()).isEqualTo("My Playlist 2");

        verify(tokenProvider).getAccessToken();
        verify(playlistPort).getListOfCurrentUsersPlaylistsAsync(ACCESS_TOKEN);
    }

    @Test
    @DisplayName("getListOfCurrentUsersPlaylistsAsync should return empty list when no playlists exist")
    void getListOfCurrentUsersPlaylistsAsync_ReturnsEmptyList() {
        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getListOfCurrentUsersPlaylistsAsync(ACCESS_TOKEN)).thenReturn(List.of());

        List<Playlist> result = service.getListOfCurrentUsersPlaylistsAsync();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(tokenProvider).getAccessToken();
        verify(playlistPort).getListOfCurrentUsersPlaylistsAsync(ACCESS_TOKEN);
    }

    @Test
    @DisplayName("getListOfCurrentUsersPlaylistsAsync should use correct access token")
    void getListOfCurrentUsersPlaylistsAsync_UsesCorrectAccessToken() {
        String customToken = "custom-access-token-xyz";
        List<Playlist> playlists = List.of(createPlaylist("1", "Test"));

        when(tokenProvider.getAccessToken()).thenReturn(customToken);
        when(playlistPort.getListOfCurrentUsersPlaylistsAsync(customToken)).thenReturn(playlists);

        service.getListOfCurrentUsersPlaylistsAsync();

        verify(playlistPort).getListOfCurrentUsersPlaylistsAsync(customToken);
    }

    @Test
    @DisplayName("getListOfCurrentUsersPlaylistsAsync should handle multiple playlists")
    void getListOfCurrentUsersPlaylistsAsync_HandlesMultiplePlaylists() {
        List<Playlist> expectedPlaylists = List.of(
                createPlaylist("1", "Workout Mix"),
                createPlaylist("2", "Chill Vibes"),
                createPlaylist("3", "Road Trip"),
                createPlaylist("4", "Study Focus"),
                createPlaylist("5", "Party Hits")
        );

        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getListOfCurrentUsersPlaylistsAsync(ACCESS_TOKEN)).thenReturn(expectedPlaylists);

        List<Playlist> result = service.getListOfCurrentUsersPlaylistsAsync();

        assertThat(result).hasSize(5);
        assertThat(result).extracting(Playlist::getName)
                .containsExactly("Workout Mix", "Chill Vibes", "Road Trip", "Study Focus", "Party Hits");
    }

    @Test
    @DisplayName("getPlaylistTracksAsync should successfully return tracks")
    void getPlaylistTracksAsync_Success() {
        List<Track> expectedTracks = List.of(
                createTrack("track1", "Song One"),
                createTrack("track2", "Song Two")
        );

        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getPlaylistTracksAsync(ACCESS_TOKEN, PLAYLIST_ID)).thenReturn(expectedTracks);

        List<Track> result = service.getPlaylistTracksAsync(PLAYLIST_ID);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Song One");
        assertThat(result.get(1).getName()).isEqualTo("Song Two");

        verify(tokenProvider).getAccessToken();
        verify(playlistPort).getPlaylistTracksAsync(ACCESS_TOKEN, PLAYLIST_ID);
    }

    @Test
    @DisplayName("getPlaylistTracksAsync should return empty list when playlist has no tracks")
    void getPlaylistTracksAsync_ReturnsEmptyList() {
        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getPlaylistTracksAsync(ACCESS_TOKEN, PLAYLIST_ID)).thenReturn(List.of());

        List<Track> result = service.getPlaylistTracksAsync(PLAYLIST_ID);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(tokenProvider).getAccessToken();
        verify(playlistPort).getPlaylistTracksAsync(ACCESS_TOKEN, PLAYLIST_ID);
    }

    @Test
    @DisplayName("getPlaylistTracksAsync should use correct access token and playlist ID")
    void getPlaylistTracksAsync_UsesCorrectParameters() {
        String customToken = "custom-token-abc";
        String customPlaylistId = "custom-playlist-999";
        List<Track> tracks = List.of(createTrack("track1", "Test Track"));

        when(tokenProvider.getAccessToken()).thenReturn(customToken);
        when(playlistPort.getPlaylistTracksAsync(customToken, customPlaylistId)).thenReturn(tracks);

        service.getPlaylistTracksAsync(customPlaylistId);

        verify(playlistPort).getPlaylistTracksAsync(customToken, customPlaylistId);
    }

    @Test
    @DisplayName("getPlaylistTracksAsync should handle multiple tracks")
    void getPlaylistTracksAsync_HandlesMultipleTracks() {
        List<Track> expectedTracks = List.of(
                createTrack("track1", "Song A"),
                createTrack("track2", "Song B"),
                createTrack("track3", "Song C"),
                createTrack("track4", "Song D")
        );

        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getPlaylistTracksAsync(ACCESS_TOKEN, PLAYLIST_ID)).thenReturn(expectedTracks);

        List<Track> result = service.getPlaylistTracksAsync(PLAYLIST_ID);

        assertThat(result).hasSize(4);
        assertThat(result).extracting(Track::getName)
                .containsExactly("Song A", "Song B", "Song C", "Song D");
    }

    @Test
    @DisplayName("getPlaylistTracksAsync should pass playlist ID correctly")
    void getPlaylistTracksAsync_PassesPlaylistIdCorrectly() {
        String specificPlaylistId = "specific-playlist-id-789";
        List<Track> tracks = List.of(createTrack("track1", "Track"));

        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getPlaylistTracksAsync(ACCESS_TOKEN, specificPlaylistId)).thenReturn(tracks);

        service.getPlaylistTracksAsync(specificPlaylistId);

        verify(playlistPort).getPlaylistTracksAsync(ACCESS_TOKEN, specificPlaylistId);
    }

    @Test
    @DisplayName("getListOfCurrentUsersPlaylistsAsync should call tokenProvider exactly once")
    void getListOfCurrentUsersPlaylistsAsync_CallsTokenProviderOnce() {
        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getListOfCurrentUsersPlaylistsAsync(ACCESS_TOKEN)).thenReturn(List.of());

        service.getListOfCurrentUsersPlaylistsAsync();

        verify(tokenProvider, times(1)).getAccessToken();
    }

    @Test
    @DisplayName("getPlaylistTracksAsync should call tokenProvider exactly once")
    void getPlaylistTracksAsync_CallsTokenProviderOnce() {
        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getPlaylistTracksAsync(ACCESS_TOKEN, PLAYLIST_ID)).thenReturn(List.of());

        service.getPlaylistTracksAsync(PLAYLIST_ID);

        verify(tokenProvider, times(1)).getAccessToken();
    }

    @Test
    @DisplayName("getListOfCurrentUsersPlaylistsAsync should preserve playlist order")
    void getListOfCurrentUsersPlaylistsAsync_PreservesOrder() {
        List<Playlist> orderedPlaylists = List.of(
                createPlaylist("1", "First"),
                createPlaylist("2", "Second"),
                createPlaylist("3", "Third")
        );

        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getListOfCurrentUsersPlaylistsAsync(ACCESS_TOKEN)).thenReturn(orderedPlaylists);

        List<Playlist> result = service.getListOfCurrentUsersPlaylistsAsync();

        assertThat(result).extracting(Playlist::getId)
                .containsExactly("1", "2", "3");
    }

    @Test
    @DisplayName("getPlaylistTracksAsync should preserve track order")
    void getPlaylistTracksAsync_PreservesOrder() {
        List<Track> orderedTracks = List.of(
                createTrack("track1", "First Track"),
                createTrack("track2", "Second Track"),
                createTrack("track3", "Third Track")
        );

        when(tokenProvider.getAccessToken()).thenReturn(ACCESS_TOKEN);
        when(playlistPort.getPlaylistTracksAsync(ACCESS_TOKEN, PLAYLIST_ID)).thenReturn(orderedTracks);

        List<Track> result = service.getPlaylistTracksAsync(PLAYLIST_ID);

        assertThat(result).extracting(Track::getName)
                .containsExactly("First Track", "Second Track", "Third Track");
    }

    private Playlist createPlaylist(String id, String name) {
        return new Playlist(
                id,
                name,
                "owner123",
                "Owner Name",
                "Test playlist description",
                false,
                true,
                0,
                "https://i.scdn.co/image/test",
                List.of(),
                "https://open.spotify.com/playlist/" + id
        );
    }

    private Track createTrack(String trackId, String name) {
        return new Track(
                TrackId.fromSpotifyId(trackId),
                name,
                "Test Artist",
                "Test Album",
                180000,
                "https://open.spotify.com/track/" + trackId,
                "https://p.scdn.co/mp3-preview/" + trackId,
                "https://i.scdn.co/image/" + trackId
        );
    }
}
