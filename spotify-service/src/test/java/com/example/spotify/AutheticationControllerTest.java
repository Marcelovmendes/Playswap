package com.example.spotify;



import com.example.spotify.auth.api.AutheticationController;
import com.example.spotify.auth.application.AuthenticationService;
import com.example.spotify.auth.domain.entity.OAuth2Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import java.net.URI;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AutheticationController controller;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @Test
    void initiateAuthentication_ShouldRedirectToAuthUri() throws Exception {
        // Arrange
        URI authUri = new URI("https://accounts.spotify.com/authorize?params=test");
        when(authService.initiateAuthentication()).thenReturn(authUri);

        // Act
        String result = controller.initiateAuthentication();

        // Assert
        assertEquals("redirect:" + authUri.toString(), result);
        verify(authService).initiateAuthentication();
    }

    @Test
    void initiateAuthentication_WithException_ShouldRedirectToError() throws Exception {
        // Arrange
        when(authService.initiateAuthentication()).thenThrow(new RuntimeException("Test error"));

        // Act
        String result = controller.initiateAuthentication();

        // Assert
        assertEquals("redirect:/error?reason=auth_initialization_failed", result);
        verify(authService).initiateAuthentication();
    }

    @Test
    void handleCallback_WithError_ShouldRedirectToErrorPage() {
        // Arrange
        String error = "access_denied";

        // Act
        String result = controller.handleCallback(null, null, error, session);

        // Assert
        assertEquals("redirect:/error?reason=access_denied", result);
        verifyNoInteractions(authService);
    }

    @Test
    void handleCallback_WithValidParams_ShouldExchangeCodeAndRedirect() {
        // Arrange
        String code = "valid_auth_code";
        String state = "valid_state";

        OAuth2Token token = mock(OAuth2Token.class);
        when(token.getAccessToken()).thenReturn("access_token_123");
        when(token.getRefreshToken()).thenReturn("refresh_token_456");
        when(token.getExpiresAt()).thenReturn(Instant.now().plusSeconds(3600));

        when(authService.handleAuthenticationCallback(code, state)).thenReturn(token);

        // Act
        String result = controller.handleCallback(code, state, null, session);

        // Assert
        assertEquals("redirect:/spotify/dashboard", result);
        assertEquals("access_token_123", session.getAttribute("spotifyAccessToken"));
        assertEquals("refresh_token_456", session.getAttribute("spotifyRefreshToken"));
        verify(authService).handleAuthenticationCallback(code, state);
    }

    @Test
    void handleCallback_WithInvalidState_ShouldRedirectWithError() {
        // Arrange
        String code = "valid_code";
        String state = "invalid_state";

        when(authService.handleAuthenticationCallback(code, state))
                .thenThrow(new Exception("Test error"));

        // Act
        String result = controller.handleCallback(code, state, null, session);

        // Assert
        assertEquals("redirect:/error?reason=invalid_state", result);
        verify(authService).handleAuthenticationCallback(code, state);
    }

    @Test
    void handleCallback_WithAuthenticationError_ShouldRedirectWithError() {
        // Arrange
        String code = "valid_code";
        String state = "valid_state";

        when(authService.handleAuthenticationCallback(code, state))
                .thenThrow(new Exception("Authentication failed"));

        // Act
        String result = controller.handleCallback(code, state, null, session);

        // Assert
        assertEquals("redirect:/error?reason=authentication_failed", result);
        verify(authService).handleAuthenticationCallback(code, state);
    }

    @Test
    void handleCallback_WithUnexpectedError_ShouldRedirectWithGenericError() {
        // Arrange
        String code = "valid_code";
        String state = "valid_state";

        when(authService.handleAuthenticationCallback(code, state))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act
        String result = controller.handleCallback(code, state, null, session);

        // Assert
        assertEquals("redirect:/error?reason=unexpected_error", result);
        verify(authService).handleAuthenticationCallback(code, state);
    }
}