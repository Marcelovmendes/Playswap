package com.example.spotify.playlist.infrastructure.persistence;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "playlists", schema = "spotify")
public class PlayListJdbcEntity {
    @Id
    @Column("id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("owner_id")
    private UUID ownerId;

    @Column("description")
    private String description;

    @Column("collaborative")
    private boolean collaborative;

    @Column("public_access")
    private boolean publicAccess;

    @Column("track_count")
    private int trackCount;

    @Column("image")
    private String image;

    @Column("external_urls")
    private String externalUrls;

    @Column("platform")
    private String platform;

    @Column("conversion_requested")
    private boolean conversionRequested;

    @Column("target_platform")
    private String targetPlatform;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Deprecated
    private PlayListJdbcEntity() {}

    public PlayListJdbcEntity(UUID id, String name, UUID ownerId, String description, boolean collaborative,
                               boolean publicAccess, int trackCount, String imageUrl, String externalUrl,
                               String platform, boolean conversionRequested, String targetPlatform,
                               LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.collaborative = collaborative;
        this.publicAccess = publicAccess;
        this.trackCount = trackCount;
        this.image = imageUrl;
        this.externalUrls = externalUrl;
        this.platform = platform;
        this.conversionRequested = conversionRequested;
        this.targetPlatform = targetPlatform;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public UUID getOwnerId() { return ownerId; }
    public String getDescription() { return description; }
    public boolean isCollaborative() { return collaborative; }
    public boolean isPublicAccess() { return publicAccess; }
    public int getTrackCount() { return trackCount; }
    public String getImage() { return image; }
    public String getExternalUrl() { return externalUrls; }
    public String getPlatform() { return platform; }
    public boolean isConversionRequested() { return conversionRequested; }
    public String getTargetPlatform() { return targetPlatform; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
