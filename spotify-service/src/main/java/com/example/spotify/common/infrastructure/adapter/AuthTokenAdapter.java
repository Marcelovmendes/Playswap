package com.example.spotify.common.infrastructure.adapter;

import com.example.spotify.auth.application.TokenQuery;
import com.example.spotify.auth.domain.entity.Token;
import com.example.spotify.common.infrastructure.service.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenAdapter implements TokenProvider {
    private final TokenQuery tokenQuery;

    public AuthTokenAdapter(TokenQuery tokenQuery) {
        this.tokenQuery = tokenQuery;
    }

    @Override
    public String getAccessToken() {
        return tokenQuery.getCurrentUserToken()
                .map(Token::getAccessToken)
                .orElse(null);
    }

    @Override
    public boolean isTokenValid() {
        return tokenQuery.isUserAuthenticated();
    }
}
