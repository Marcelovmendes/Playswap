package com.example.spotify.playlist.domain.entity;

import com.example.spotify.user.domain.entity.UserId;

import java.util.Objects;

public class PlaylistSummary {
    private final PlaylistId id;
    private final String name;
    private final UserId ownerId;
    private final String ownerName;
    private final Integer trackCount;
    private final String imageUrl;
    private final String externalUrl;
    private final boolean collaborative;
    private final boolean publicAccess;
    private final String plataform;

    public PlaylistSummary(PlaylistId id, String name, UserId ownerId, String ownerName, Integer trackCount, String imageUrl, String externalUrl,
                           boolean collaborative, boolean publicAccess, String platform) {
        this.id = Objects.requireNonNull(id, "Playlist ID cannot be null");
        this.name = Objects.requireNonNull(name, "Playlist name cannot be null");
        this.ownerId = Objects.requireNonNull(ownerId, "Owner ID cannot be null");
        this.ownerName = ownerName;
        this.trackCount = trackCount;
        this.imageUrl = imageUrl;
        this.externalUrl = externalUrl;
        this.collaborative = collaborative;
        this.publicAccess = publicAccess;
        this.plataform = platform;
    }
    public static PlaylistSummary fromPlaylist(Playlist playlist, String ownerName) {
        return new PlaylistSummary(
                playlist.getId(),
                playlist.getName(),
                playlist.getOwnerId(),
                ownerName,
                playlist.getTrackCount(),
                playlist.getImageUrl(),
                playlist.getExternalUrl(),
                playlist.isCollaborative(),
                playlist.isPublicAccess(),
                "Spotify"
        );
    }
    public static PlaylistSummary fromPlaylistAggregate(PlaylistAggregate playlistAggregate, String ownerName) {
        return new PlaylistSummary(
                playlistAggregate.getId(),
                playlistAggregate.getName(),
                playlistAggregate.getOwnerId(),
                ownerName,
                playlistAggregate.getTrackCount(),
                playlistAggregate.getPlaylist().getImageUrl(),
                playlistAggregate.getPlaylist().getExternalUrl(),
                playlistAggregate.getPlaylist().isCollaborative(),
                playlistAggregate.getPlaylist().isPublicAccess(),
                "Spotify"
        );
    }
    public PlaylistId getId() { return id; }
    public String getName() { return name; }
    public UserId getOwnerId() { return ownerId; }
    public String getOwnerName() { return ownerName; }
    public int getTrackCount() { return trackCount; }
    public String getImageUrl() { return imageUrl; }
    public String getExternalUrl() { return externalUrl; }
    public boolean isCollaborative() { return collaborative; }
    public boolean isPublicAccess() { return publicAccess; }
    public String getPlatform() { return plataform; }

}
