package com.example.spotify.auth.domain.service;

public interface PkceService {

    String generateCodeVerifier();
    String generateCodeChallenge(String codeVerifier);
}
