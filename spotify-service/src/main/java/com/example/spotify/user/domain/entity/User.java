package com.example.spotify.user.domain.entity;

import com.example.spotify.auth.domain.service.UserToken;
import jakarta.annotation.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

//TODO Ver quais campos vao ser necessários


@Table(name = "users", schema = "spotify")
public class User {
    @Id
    private String id;

    @Nullable
    private LocalDate birthdate;
    private String country;
    private String displayName;
    private String email;
    private String externalUrls;
    private int followersCount;
    private String href;
    @Nullable
    private String photoCover;
    private String spotifyUri;
    private String type;
    private String accesstoken;
    private String refreshtoken;
    private LocalDateTime tokenExpiry;
    @CreatedDate
    private LocalDateTime firstSeen;

    @LastModifiedDate
    private LocalDateTime last_seen;

    private String userRegistredId;

    @Deprecated
    public User() {}

    public User(String id, LocalDate birthdate, String country, String displayName, String email, String externalUrls,
                int followersCount, String href, String photoCover, String spotifyUri, String type) {
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
    }

    public String getId() { return id; }
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
    public LocalDateTime getFirstSeen() { return firstSeen; }
    public LocalDateTime getLastSeen() { return last_seen; }
    public String getUserRegistredId() { return userRegistredId; }

    public User updateToken(UserToken newToken) {
        this.accesstoken = newToken.getAccessToken();
        this.refreshtoken = newToken.getRefreshToken();
        this.tokenExpiry = LocalDateTime.from(newToken.getExpiresAt());
        this.last_seen = LocalDateTime.now();
        return this;
    }



}
