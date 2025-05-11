package com.example.spotify.auth.api;
import com.example.spotify.auth.application.AuthUseCase;
import com.example.spotify.auth.application.TokenQuery;
import com.example.spotify.auth.domain.entity.Token;
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

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthUseCase authService;
    private final TokenStoragePort tokenStoragePort;
    private final TokenQuery tokenQuery;
    private final AuthUseCase authUseCase;

    public AuthenticationController(AuthUseCase authService, TokenStoragePort tokenStoragePort, TokenQuery tokenQuery, AuthUseCase authUseCase) {
        this.authService = authService;
        this.tokenStoragePort = tokenStoragePort;
        this.tokenQuery = tokenQuery;
        this.authUseCase = authUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<String> initiateAuthentication(){
            URI response = authUseCase.initiateAuthentication();
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
            Token token = authUseCase.completeAuthentication(code, state);
            tokenQuery.storeUserToken(session.getId(), token);

            log.info("Autenticação concluída e enviada pra sessão com sucesso!");
            return ResponseEntity.ok("Autenticação concluída!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}