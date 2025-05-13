package com.example.spotify.playlist.domain.repository;

import com.example.spotify.playlist.domain.entity.Track;
import com.example.spotify.playlist.domain.entity.TrackId;

import java.util.List;

public interface TrackRepository {
     Track findById(TrackId id);
     Track save(Track track);
     List<Track> findByAllIds(List<TrackId>ids);
     List<Track> saveAll(List<Track> tracks);
     void deleteById(TrackId id);
     boolean existsById(TrackId id);
}
