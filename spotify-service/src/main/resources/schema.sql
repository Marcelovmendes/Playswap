CREATE SCHEMA IF NOT EXISTS spotify;
SET search_path TO spotify;

CREATE TABLE IF NOT EXISTS users (
                                     id                  UUID PRIMARY KEY,
                                     spotify_id          VARCHAR(255) NOT NULL UNIQUE,
                                     birth_date          DATE,
                                     country             VARCHAR(10),
                                     display_name        VARCHAR(255) NOT NULL,
                                     email               VARCHAR(255) UNIQUE,
                                     external_urls       VARCHAR(500),
                                     followers_count     INTEGER DEFAULT 0,
                                     hrf                 VARCHAR(255),
                                     photo_cover         VARCHAR(255),
                                     spotify_uri         VARCHAR(255),
                                     type                VARCHAR(50),
                                     registered_user_id  VARCHAR(255),
                                     created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS playlists (
                                         id                  UUID PRIMARY KEY,
                                         spotify_id          VARCHAR(255) NOT NULL UNIQUE,
                                         name                VARCHAR(255) NOT NULL,
                                         owner_id            UUID NOT NULL REFERENCES spotify.users(id) ON DELETE CASCADE,
                                         description         VARCHAR(255),
                                         collaborative       BOOLEAN NOT NULL DEFAULT FALSE,
                                         public_access       BOOLEAN NOT NULL DEFAULT FALSE,
                                         track_count         INTEGER NOT NULL DEFAULT 0,
                                         image               VARCHAR(1000),
                                         external_urls       VARCHAR(700),
                                         platform            VARCHAR(50) NOT NULL DEFAULT 'spotify',
                                         conversion_requested BOOLEAN NOT NULL DEFAULT FALSE,
                                         target_platform     VARCHAR(50),
                                         created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tracks (
                                      id                  UUID PRIMARY KEY,
                                      spotify_id          VARCHAR(255) NOT NULL UNIQUE,
                                      name                VARCHAR(255) NOT NULL,
                                      artist              VARCHAR(255),
                                      album               VARCHAR(255),
                                      duration_ms         INTEGER,
                                      external_urls       VARCHAR(500),
                                      preview_url         VARCHAR(500),
                                      image               VARCHAR(1000),
                                      platform            VARCHAR(50) NOT NULL DEFAULT 'spotify',
                                      created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS playlist_tracks (
                                               id              SERIAL PRIMARY KEY,
                                               playlist_id     UUID NOT NULL REFERENCES spotify.playlists(id) ON DELETE CASCADE,
                                               track_id        UUID NOT NULL REFERENCES spotify.tracks(id) ON DELETE CASCADE,
                                               position        INTEGER NOT NULL,
                                               added_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                               CONSTRAINT uq_playlist_track UNIQUE (playlist_id, track_id, position)
);

CREATE INDEX IF NOT EXISTS idx_playlists_owner_id ON playlists(owner_id);
CREATE INDEX IF NOT EXISTS idx_playlists_platform ON playlists(platform);
CREATE INDEX IF NOT EXISTS idx_tracks_platform ON tracks(platform);
CREATE INDEX IF NOT EXISTS idx_playlist_tracks_playlist_id ON playlist_tracks(playlist_id);
CREATE INDEX IF NOT EXISTS idx_playlist_tracks_track_id ON playlist_tracks(track_id);
CREATE INDEX IF NOT EXISTS idx_playlist_tracks_position ON playlist_tracks(position);
