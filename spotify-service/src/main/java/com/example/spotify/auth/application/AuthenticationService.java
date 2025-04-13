package com.example.spotify.auth.application;

import com.example.spotify.auth.domain.entity.AuthenticationToken;

import javax.naming.AuthenticationException;
import java.net.URI;

public interface AuthenticationService {
    URI initiateAuthentication() throws AuthenticationException;
    AuthenticationToken handleAuthenticationCallback(String code, String state);
}
