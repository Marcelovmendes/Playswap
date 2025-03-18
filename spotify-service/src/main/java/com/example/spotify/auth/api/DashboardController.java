package com.example.spotify.auth.api;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("spotify")
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String accessToken = (String) session.getAttribute("spotifyAccessToken");
        if (accessToken == null) {
            log.warn("Tentativa de acesso ao dashboard sem token");
            return "redirect:/auth/spotify";
        }
        model.addAttribute("authenticated", true);


        return "spotify/dashboard"; // Esta é a view que será renderizada
    }
}
