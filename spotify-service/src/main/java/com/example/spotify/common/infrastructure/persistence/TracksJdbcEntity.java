package com.example.spotify.common.infrastructure.persistence;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="tracks", schema = "spotify")
public class TracksJdbcEntity {

    @Id
    private String id;
    private String name;
    private String artist;
    private String album;

    @Column("duration_ms")
    private String durationMs;

    @Column("external_url")
    private String externalUrl;

    @Column("preview_url")
    private String previewUrl;

    @Column("image_url")
    private String imageUrl;

    @Column("platform")
    private String platform;

    @CreatedDate
    @Column("created_at")
    private String createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private String updatedAt;

    @Deprecated
    protected TracksJdbcEntity() {}

    public TracksJdbcEntity(String id, String name, String artist, String album, String durationMs,
                             String externalUrl, String previewUrl, String imageUrl, String platform,
                             String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.durationMs = durationMs;
        this.externalUrl = externalUrl;
        this.previewUrl = previewUrl;
        this.imageUrl = imageUrl;
        this.platform = platform;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public String getDurationMs() { return durationMs; }
    public String getExternalUrl() { return externalUrl; }
    public String getPreviewUrl() { return previewUrl; }
    public String getImageUrl() { return imageUrl; }
    public String getPlatform() { return platform; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

}
