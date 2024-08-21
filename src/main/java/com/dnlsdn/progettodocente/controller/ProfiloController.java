package com.dnlsdn.progettodocente.controller;

import com.dnlsdn.progettodocente.model.Credenziali;
import com.dnlsdn.progettodocente.model.Giocatore;
import com.dnlsdn.progettodocente.model.Presidente;
import com.dnlsdn.progettodocente.service.GiocatoreService;
import com.dnlsdn.progettodocente.service.PresidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfiloController {

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private PresidenteService presidenteService;

    @GetMapping("/profilo")
    public String mostraProfilo(Model model, @AuthenticationPrincipal Credenziali utente) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ruolo = null;
        String username = auth.getName();

        // Controlla il ruolo dell'utente autenticato
        for (GrantedAuthority authority : auth.getAuthorities()) {
            ruolo = authority.getAuthority();
        }

        model.addAttribute("ruolo", ruolo);
        if ("GIOCATORE".equals(ruolo)) {
            System.out.println("QUIIIII");
            System.out.println("Username: " + username);
            Giocatore giocatore = giocatoreService.findByCredenzialiUsername(username);
            System.out.println("Giocatore: " + giocatore);
            model.addAttribute("utente", giocatore);
        } else if ("PRESIDENTE".equals(ruolo)) {
            Presidente presidente = presidenteService.findByCredenzialiUsername(username);
            model.addAttribute("utente", presidente);
        }

        return "profilo";
    }
}
