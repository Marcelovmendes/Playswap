

CREATE SCHEMA IF NOT EXISTS spotify;

SET search_path TO spotify;

CREATE TABLE IF NOT EXISTS spotify.users (
     id uuid PRIMARY KEY,
     birth_date DATE NULL,
     country VARCHAR(10),
     display_name VARCHAR(255),
     email VARCHAR(255),
     external_urls VARCHAR(500),
     followers_count INTEGER,
     hrf VARCHAR(255),
     photo_cover VARCHAR(255) NULL,
     spotify_uri VARCHAR(255),
     "type" VARCHAR(50),
     first_seen TIMESTAMP,
     last_seen TIMESTAMP,
     registered_user_id VARCHAR(255) NULL
)