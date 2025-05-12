

CREATE SCHEMA IF NOT EXISTS spotify;

SET search_path TO spotify;

CREATE TABLE IF NOT EXISTS spotify.users
(
    id                 uuid PRIMARY KEY,
    birth_date         DATE         NULL,
    country            VARCHAR(10),
    display_name       VARCHAR(255),
    email              VARCHAR(255),
    external_urls      VARCHAR(500),
    followers_count    INTEGER,
    hrf                VARCHAR(255),
    photo_cover        VARCHAR(255) NULL,
    spotify_uri        VARCHAR(255),
    "type"             VARCHAR(50),
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP,
    registered_user_id VARCHAR(255) NULL
);
CREATE TABLE IF NOT EXISTS spotify.playlists
(
    id            uuid PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    owner_id      uuid NOT NULL REFERENCES spotify.users(id),
    description   VARCHAR(255),
    collaborative BOOLEAN NOT NULL DEFAULT FALSE,
    public_access BOOLEAN NOT NULL DEFAULT FALSE,
    track_count   INTEGER NOT NULL DEFAULT 0,
    image         VARCHAR(1000),
    external_urls VARCHAR(700),
    platform      VARCHAR(50),
    conversion_requested BOOLEAN NOT NULL DEFAULT FALSE,
    target_platform VARCHAR(50),
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS spotify.tracks
(
    id            uuid PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    artist        VARCHAR(255),
    album         VARCHAR(255),
    duration_ms   INTEGER,
    external_urls VARCHAR(500),
    preview_url   VARCHAR(500),
    image         VARCHAR(1000),
    platform      VARCHAR(50),
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS spotify.playlist_tracks
(
    id            SERIAL PRIMARY KEY,
    playlist_id   uuid NOT NULL REFERENCES spotify.playlists(id) ON DELETE CASCADE,
    track_id      uuid NOT NULL REFERENCES spotify.tracks(id) ON DELETE CASCADE,
    position      INTEGER NOT NULL,
    added_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    unique        (playlist_id, track_id, position)
);
CREATE INDEX IF NOT EXISTS idx_playlists_owner_id ON spotify.playlists(owner_id);
CREATE INDEX IF NOT EXISTS idx_playlists_platform ON spotify.playlists(platform);
CREATE INDEX IF NOT EXISTS idx_tracks_platform ON spotify.tracks(platform);
CREATE INDEX IF NOT EXISTS idx_playlist_tracks_playlist_id ON spotify.playlist_tracks(playlist_id);
CREATE INDEX IF NOT EXISTS idx_playlist_tracks_track_id ON spotify.playlist_tracks(track_id);
CREATE INDEX IF NOT EXISTS idx_playlist_tracks_position ON spotify.playlist_tracks(position);
