package com.example.spotify.auth.infrastructure.service;

import com.example.spotify.auth.domain.service.PkceService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class DefaultPkceService implements PkceService {
    @Override
    public String generateCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[64]; //64 bytes = 512 bits
        secureRandom.nextBytes(bytes);

        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return encoded.replaceAll("[^a-zA-Z0-9\\-._~]", "");
    }
    @Override
    public String generateCodeChallenge(String codeVerifier) {
        try {
            byte[] verifierByte = codeVerifier.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(verifierByte);

            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
