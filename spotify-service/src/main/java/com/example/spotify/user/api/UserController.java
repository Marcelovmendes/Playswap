package com.example.spotify.user.api;

import com.example.spotify.auth.domain.service.TokenStoragePort;
import com.example.spotify.auth.domain.service.UserToken;
import com.example.spotify.user.api.dto.UserProfileDTO;
import com.example.spotify.user.application.UserServicePort;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/user/")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final TokenStoragePort tokenStorage;
    private final UserServicePort userService;

    public UserController(TokenStoragePort tokenStorage, UserServicePort userService) {
        this.tokenStorage = tokenStorage;
        this.userService = userService;
    }

    @GetMapping("/details")
    public ResponseEntity<?> showDashboard(HttpSession session) {
          try {
              log.info("Callback request ID: {}", session.getId());
              String accessToken = (String) session.getAttribute("spotifyAccessToken");
              if (accessToken == null) {
                  log.warn("Tentativa de acesso ao dashboard sem token");
              }
              UserToken token = tokenStorage.retrieveUserToken();
              UserProfileDTO user = userService.getCurrentUserProfileAsync(token);

              return new ResponseEntity<>(user, HttpStatus.OK);

          } catch (Exception e) {
              return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }
}
