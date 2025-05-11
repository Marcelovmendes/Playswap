package com.example.spotify.common.infrastructure.persistence;

import jakarta.annotation.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "users", schema = "spotify")
public class UserJdbcEntity {
    @Id
    @Column("id")
    private UUID id;

    @Column("birth_date")
    private LocalDate birthdate;
    private String country;
    @Column("display_name")
    private String displayName;
    private String email;
    private String externalUrls;
    @Column("followers_count")
    private int followersCount;
    @Column("hrf")
    private String href;
    @Column("photo_cover")
    private String photoCover;
    @Column("spotify_uri")
    private String spotifyUri;
    private String type;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("registered_user_id")
    private String userRegisteredId;

    @Deprecated
    public UserJdbcEntity() {
    }

    public UserJdbcEntity(UUID id, LocalDate birthdate, String country, String displayName, String email,
                          String externalUrls, int followersCount, String href, String photoCover,
                          String spotifyUri, String type, @Nullable String userRegisteredId) {
        this.id = id;
        this.birthdate = birthdate;
        this.country = country;
        this.displayName = displayName;
        this.email = email;
        this.externalUrls = externalUrls;
        this.followersCount = followersCount;
        this.href = href;
        this.photoCover = photoCover;
        this.spotifyUri = spotifyUri;
        this.type = type;
        this.userRegisteredId = userRegisteredId;
    }

    // Getters e setters
    public UUID getId() { return id; }
    public LocalDate getBirthdate() { return birthdate; }
    public String getCountry() { return country; }
    public String getDisplayName() { return displayName; }
    public String getEmail() { return email; }
    public String getExternalUrls() { return externalUrls; }
    public int getFollowersCount() { return followersCount; }
    public String getHref() { return href; }
    public String getPhotoCover() { return photoCover; }
    public String getSpotifyUri() { return spotifyUri; }
    public String getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getUserRegisteredId() { return userRegisteredId; }
}
