package com.example.spotify.auth.domain.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AuthorizationResquest {
    private final String codeChallenge;
    private final String state;
    private final Set<String> scopes;

    private AuthorizationResquest(String codeChallenge, String state, Set<String> scopes) {
        this.codeChallenge = codeChallenge;
        this.state = state;
        this.scopes = Collections.unmodifiableSet(new HashSet<>(scopes));
    }

    public static AuthorizationResquest create(String codeChallenge, String state, Set<String> scopes) {
        return new AuthorizationResquest(codeChallenge, state, scopes);
    }

    public String getCodeChallenge() { return codeChallenge; }
    public String getState() { return state; }
    public Set<String> getScopes() { return scopes; }

    public String getScopesAsString() {
        return String.join(" ", scopes);
    }
}
