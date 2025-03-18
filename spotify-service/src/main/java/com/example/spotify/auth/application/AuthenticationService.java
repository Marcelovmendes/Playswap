package com.example.spotify.auth.application;

import com.example.spotify.auth.domain.entity.OAuth2Token;

import javax.naming.AuthenticationException;
import java.net.URI;

public interface AuthenticationService {
    URI initiateAuthentication() throws AuthenticationException;
    OAuth2Token handleAuthenticationCallback(String code, String state);
}
