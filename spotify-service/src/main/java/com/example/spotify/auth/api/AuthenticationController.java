package com.example.spotify.auth.api;
import com.example.spotify.auth.application.AuthenticationService;
import com.example.spotify.auth.application.impl.SpotifyAuthenticationService;
import com.example.spotify.auth.domain.exception.AuthenticationException;
import com.example.spotify.auth.domain.service.TokenStorageService;
import com.example.spotify.auth.domain.service.UserTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authService;
    private final TokenStorageService tokenStorageService;

    public AuthenticationController(SpotifyAuthenticationService authService, TokenStorageService tokenStorageService) {
        this.authService = authService;
        this.tokenStorageService = tokenStorageService;
    }

    @GetMapping("/spotify")
    public ResponseEntity<String> initiateAuthentication() {
        try {
            URI response = authService.initiateAuthentication();

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Falha ao iniciar autenticação", e);
            return new ResponseEntity<>( "Falha ao iniciar autenticação do usuário",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/callback")
    public String handleCallback(
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
            UserTokenService token = authService.handleAuthenticationCallback(code, state);
            tokenStorageService.storeUserToken(session, token);

            log.info("Autenticação concluída e enviada pra sessão com sucesso!");

            return "redirect:/spotify/dashboard";


        } catch (AuthenticationException e) {
            log.error("Erro de autenticação: {}", e.getMessage());

        } catch (Exception e) {
            log.error("Erro inesperado no callback", e);

        }
        return "redirect:/spotify/dashboard";
    }
}