package com.example.spotify;


import com.example.spotify.auth.domain.service.TokenStorageService;
import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.user.api.DashboardUserController;
import com.example.spotify.user.api.dto.UserProfileDTO;
import com.example.spotify.user.application.UserServiceContract;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardUserControllerTest {

    @Mock
    private TokenStorageService tokenStorage;

    @Mock
    private UserServiceContract userService;

    @InjectMocks
    private DashboardUserController controller;

    private MockHttpSession session;
    private UserTokenService mockToken;
    private UserProfileDTO mockUserProfile;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        mockToken = mock(UserTokenService.class);

        mockUserProfile = new UserProfileDTO(
                LocalDate.of(1998,6,9),
                "US",
                "Test User",
                "test@example.com",
                "https://open.spotify.com/user/test",
                100,
                "https://api.spotify.com/v1/users/test",
                "https://i.scdn.co/image/profile.jpg",
                "spotify:user:test",
                "user"
        );
    }

    @Test
    void showDashboard_WithValidToken_ShouldReturnUserProfile() throws IOException, ParseException, SpotifyWebApiException {
        // Arrange
        session.setAttribute("spotifyAccessToken", "test_token");
        when(tokenStorage.retrieveUserToken()).thenReturn(mockToken);
        when(userService.getCurrentUserProfileAsync(mockToken)).thenReturn(mockUserProfile);

        // Act
        ResponseEntity<?> response = controller.showDashboard(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(mockUserProfile, response.getBody());
        verify(tokenStorage).retrieveUserToken();
        verify(userService).getCurrentUserProfileAsync(mockToken);
    }

    @Test
    void showDashboard_WithMissingToken_ShouldWarnAndProceed() throws IOException, ParseException, SpotifyWebApiException {
        // Arrange
        when(tokenStorage.retrieveUserToken()).thenReturn(mockToken);
        when(userService.getCurrentUserProfileAsync(mockToken)).thenReturn(mockUserProfile);

        // Act
        ResponseEntity<?> response = controller.showDashboard(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(mockUserProfile, response.getBody());
        verify(tokenStorage).retrieveUserToken();
        verify(userService).getCurrentUserProfileAsync(mockToken);
    }

    @Test
    void showDashboard_WithServiceException_ShouldReturnError() throws IOException, ParseException, SpotifyWebApiException {
        // Arrange
        session.setAttribute("spotifyAccessToken", "test_token");
        when(tokenStorage.retrieveUserToken()).thenReturn(mockToken);

        String errorMessage = "Service error";
        when(userService.getCurrentUserProfileAsync(mockToken))
                .thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<?> response = controller.showDashboard(session);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        verify(tokenStorage).retrieveUserToken();
        verify(userService).getCurrentUserProfileAsync(mockToken);
    }

    @Test
    void showDashboard_WithTokenExceptionl_ShouldReturnError() {
        // Arrange
        session.setAttribute("spotifyAccessToken", "test_token");

        String errorMessage = "Token retrieval error";
        when(tokenStorage.retrieveUserToken())
                .thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<?> response = controller.showDashboard(session);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        verify(tokenStorage).retrieveUserToken();
        verifyNoInteractions(userService);
    }
};