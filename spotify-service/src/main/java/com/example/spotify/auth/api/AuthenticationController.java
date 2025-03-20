package com.example.spotify.auth.api;
import com.example.spotify.auth.application.impl.SpotifyAuthenticationService;
import com.example.spotify.auth.domain.entity.OAuth2Token;
import com.example.spotify.auth.domain.service.TokenStorageService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@Controller
@RequestMapping("auth")
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authService;
    private final TokenStorageService tokenStorageService;

    public AuthenticationController(SpotifyAuthenticationService authService, TokenStorageService tokenStorageService) {
        this.authService = authService;
        this.tokenStorageService = tokenStorageService;
    }

    @GetMapping("/spotify")
    public String initiateAuthentication() {
        try {
            URI authorizationUri = authService.initiateAuthentication();
            return "redirect:" + authorizationUri.toString();
        } catch (Exception e) {
            log.error("Falha ao iniciar autenticação", e);
            return "redirect:/error?reason=auth_initialization_failed";
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
            return "redirect:/error?reason=" + error;
        }

        try {
            OAuth2Token token = authService.handleAuthenticationCallback(code, state);
            tokenStorageService.storeUserToken(session, token);


            log.info("Autenticação concluída com sucesso");
            return "redirect:/spotify/dashboard";
            
        } catch (Exception e) {
            log.error("Erro inesperado", e);
            return "redirect:/error?reason=unexpected_error";
        }
    }

}