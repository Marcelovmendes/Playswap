package com.example.spotify.playlist.domain.entity;

import com.example.spotify.user.domain.entity.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaylistAggregate {

    private final Playlist playlist;
    private final List<Track> tracks;
    private boolean conversionRequested;
    private String targetPlaylistForm;

    public PlaylistAggregate(Playlist playlist, List<Track> tracks) {
        this.playlist = Objects.requireNonNull(playlist, "Playlist cannot be null");
        this.tracks = new ArrayList<>(tracks != null ? tracks : List.of());
        this.conversionRequested = false;
        this.targetPlaylistForm = null;
    }

    public static PlaylistAggregate create(PlaylistId id, String name, UserId ownerId, String description,
                                            boolean collaborative, boolean publicAccess, int trackCount, String imageUrl,
                                            List<Track> tracks, String externalUrl) {

        Playlist playlist = new Playlist(id, name, ownerId, description, collaborative, publicAccess, trackCount,
                imageUrl, tracks, externalUrl);

        return new PlaylistAggregate(playlist, tracks);
    }
    public static PlaylistAggregate reconstitute (Playlist playlist, List<Track> tracks) {
        return new PlaylistAggregate(playlist, tracks);
    }

    public void addTrack(Track track) {
        Objects.requireNonNull(track, "Track cannot be null");
        if (tracks.stream().noneMatch(t -> t.getId().equals(track.getId()))) {
            tracks.add(track);
        }
    }

    public boolean isEmpty() { return tracks.isEmpty(); }
    public Playlist getPlaylist() { return playlist; }
    public List<Track> getTracks() { return List.copyOf(tracks);}
    public PlaylistId getId() { return playlist.getId(); }
    public String getName() { return playlist.getName(); }
    public UserId getOwnerId() { return playlist.getOwnerId(); }
    public Integer getTrackCount() { return playlist.getTrackCount(); }

    //conversion aqui?
}
