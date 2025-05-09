package com.example.spotify.auth.api;
import com.example.spotify.auth.application.AuthenticationService;
import com.example.spotify.auth.application.impl.SpotifyAuthenticationService;
import com.example.spotify.auth.domain.service.TokenStoragePort;
import com.example.spotify.auth.domain.service.UserToken;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authService;
    private final TokenStoragePort tokenStoragePort;

    public AuthenticationController(AuthenticationService authService, TokenStoragePort tokenStoragePort) {
        this.authService = authService;
        this.tokenStoragePort = tokenStoragePort;
    }

    @GetMapping("/")
    public ResponseEntity<String> initiateAuthentication(){
            URI response = authService.initiateAuthentication();

            return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String error,
            HttpSession session) {


        log.info("Callback session ID: {}", session.getId());
        log.info("Callback recebido. Code presente: {}, State: {}, Error: {}",
                code != null, state, error != null ? error : "nenhum");

        if (error != null) {
            log.error("Erro retornado pelo provedor: {}", error);

        }
        try {
            UserToken token = authService.handleAuthenticationCallback(code, state);
            tokenStoragePort.storeUserToken(session, token);

            log.info("Autenticação concluída e enviada pra sessão com sucesso!");
            return ResponseEntity.ok("""
        <!DOCTYPE html>
        <html>
        <body>
            <p>Autenticação concluída!</p>
        </body>
        </html>
        """);
        } catch (Exception e) {
            new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}