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
    public void setId(UUID id) { this.id = id; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getExternalUrls() { return externalUrls; }
    public void setExternalUrls(String externalUrls) { this.externalUrls = externalUrls; }

    public int getFollowersCount() { return followersCount; }
    public void setFollowersCount(int followersCount) { this.followersCount = followersCount; }

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    public String getPhotoCover() { return photoCover; }
    public void setPhotoCover(String photoCover) { this.photoCover = photoCover; }

    public String getSpotifyUri() { return spotifyUri; }
    public void setSpotifyUri(String spotifyUri) { this.spotifyUri = spotifyUri; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUserRegisteredId() { return userRegisteredId; }
    public void setUserRegisteredId(String userRegisteredId) { this.userRegisteredId = userRegisteredId; }
}
