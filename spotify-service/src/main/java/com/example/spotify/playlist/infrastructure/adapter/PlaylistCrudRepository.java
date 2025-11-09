package com.example.spotify.playlist.infrastructure.adapter;

import com.example.spotify.playlist.infrastructure.persistence.PlayListJdbcEntity;
import com.example.spotify.playlist.infrastructure.repository.PlaylistJdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistCrudRepository {

    private final PlaylistJdbcRepository playlistRepo;
    private final JdbcAggregateTemplate template;



    public PlaylistCrudRepository(PlaylistJdbcRepository playlistRepo, JdbcAggregateTemplate template) {
        this.playlistRepo = playlistRepo;
        this.template = template;
    }

    public PlayListJdbcEntity insert(PlayListJdbcEntity entity) {
        return template.insert(entity);
    }

    public PlayListJdbcEntity save(PlayListJdbcEntity entity) {
        return playlistRepo.save(entity);
    }

    public Optional<PlayListJdbcEntity> findBySpotifyId(String spotifyId) {
        return playlistRepo.findBySpotifyId(spotifyId);
    }


}
