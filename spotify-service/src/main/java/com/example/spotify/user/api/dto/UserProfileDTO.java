package com.example.spotify.user.api.dto;

import java.time.LocalDate;

public record UserProfileDTO (
        LocalDate birthdate,
        String country,
        String displayName,
        String email,
        String externalUrls,
        int followersCount,
        String href,
        String photoCover,
        String spotifyUri,
        String type
) {
    public UserProfileDTO(LocalDate birthdate, String country, String displayName, String email, String externalUrls, int followersCount, String href, String photoCover, String spotifyUri, String type) {
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
}
