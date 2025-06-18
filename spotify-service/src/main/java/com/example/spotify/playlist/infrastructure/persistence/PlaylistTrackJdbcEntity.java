package com.example.spotify.playlist.infrastructure.persistence;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "playlist_tracks", schema = "spotify")
public class PlaylistTrackJdbcEntity {

    @Id
    private String id;

    @Column("playlist_id")
    private String playlistId;

    @Column("track_id")
    private String trackId;

    @Column("position")
    private int position;

    @CreatedDate
    @Column("created_at")
    private String createdAt;

    @Column("updated_at")
    private String updatedAt;

    @Deprecated
    public PlaylistTrackJdbcEntity() {}

    public PlaylistTrackJdbcEntity(String id, String playlistId, String trackId, int position, String createdAt, String updatedAt) {
        this.id = id;
        this.playlistId = playlistId;
        this.trackId = trackId;
        this.position = position;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public String getPlaylistId() { return playlistId; }
    public String getTrackId() { return trackId; }
    public int getPosition() { return position; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }


}
