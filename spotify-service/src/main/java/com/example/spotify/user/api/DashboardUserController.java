package com.example.spotify.user.api;

import com.example.spotify.auth.domain.entity.OAuth2Token;
import com.example.spotify.auth.domain.service.TokenStorageService;
import com.example.spotify.auth.domain.service.UserTokenService;
import com.example.spotify.auth.infrastructure.service.SessionTokenStorageService;
import com.example.spotify.user.application.UserServiceContract;
import jakarta.servlet.http.HttpSession;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("spotify")
public class DashboardUserController {

    private static final Logger log = LoggerFactory.getLogger(DashboardUserController.class);
    private final TokenStorageService tokenStorage;
    private final UserServiceContract userService;

    public DashboardUserController(TokenStorageService tokenStorage, UserServiceContract userService) {
        this.tokenStorage = tokenStorage;
        this.userService = userService;
    }

    @GetMapping("dashboard")
    public ResponseEntity<?> showDashboard(HttpSession session) throws IOException, ParseException, SpotifyWebApiException {
              log.info("Callback request ID: {}", session.getId());
              String accessToken = (String) session.getAttribute("spotifyAccessToken");
              if (accessToken == null) {
                  log.warn("Tentativa de acesso ao dashboard sem token");
              }
              UserTokenService token = tokenStorage.retrieveUserToken(session.getId());
              User user = userService.getCurrentUserProfileAsync(token).join();

              return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
