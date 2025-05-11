package com.example.spotify.user.api;

import com.example.spotify.auth.domain.entity.Token;
import com.example.spotify.auth.domain.service.TokenStoragePort;
import com.example.spotify.common.infrastructure.service.TokenProvider;
import com.example.spotify.user.api.dto.UserProfileDTO;
import com.example.spotify.user.application.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/spotify/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public ResponseEntity<?> showDashboard(HttpSession session) {

              log.info("Callback request ID: {}", session.getId());
              String accessToken = (String) session.getAttribute("spotifyAccessToken");
              UserProfileDTO user = userService.getCurrentUserProfileAsync();

              return ResponseEntity.ok(user);

    }
}
