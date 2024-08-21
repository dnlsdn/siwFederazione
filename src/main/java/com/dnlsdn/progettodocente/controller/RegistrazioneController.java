package com.dnlsdn.progettodocente.controller;

import com.dnlsdn.progettodocente.model.Credenziali;
import com.dnlsdn.progettodocente.model.Giocatore;
import com.dnlsdn.progettodocente.model.Presidente;
import com.dnlsdn.progettodocente.model.Squadra;
import com.dnlsdn.progettodocente.service.CredenzialiService;
import com.dnlsdn.progettodocente.service.GiocatoreService;
import com.dnlsdn.progettodocente.service.PresidenteService;
import com.dnlsdn.progettodocente.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrazioneController {

    @Autowired
    private CredenzialiService credenzialiService;

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SquadraService squadraService;

    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(value = "ruolo", required = false) Credenziali.Ruolo ruolo,
                                       Model model) {
        if (ruolo == null) {
            ruolo = Credenziali.Ruolo.GIOCATORE; // Imposta Giocatore come predefinito
        }

        model.addAttribute("ruoli", Credenziali.Ruolo.values());
        model.addAttribute("ruoloSelezionato", ruolo);

        List<Squadra> squadre = squadraService.findAll();
        model.addAttribute("squadre", squadre);

        return "register";
    }

    @GetMapping("/aggiorna")
    public String aggiorna(@RequestParam Credenziali.Ruolo ruolo, Model model) {
        model.addAttribute("ruoli", Credenziali.Ruolo.values());
        model.addAttribute("ruoloSelezionato", ruolo);
        List<Squadra> squadre = squadraService.findAll();
        List<String> nomeSquadre = new ArrayList<>();
        for (Squadra s : squadre) {
            nomeSquadre.add(s.getNome());
        }
        model.addAttribute("squadre", squadre);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String ruolo,
                               @RequestParam(required = false) String nome,
                               @RequestParam(required = false) String cognome,
                               @RequestParam(required = false) String codiceFiscale,
                               @RequestParam(required = false) String luogoNascita,
                               @RequestParam(required = false) LocalDate dataNascita,
                               @RequestParam(required = false) LocalDate inizioTesseramento,
                               @RequestParam(required = false) LocalDate fineTesseramento,
                               @RequestParam(required = false) Long squadraId,
                               @RequestParam(required = false) String ruoloGiocatore,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        Credenziali.Ruolo ruoloEnum = Credenziali.Ruolo.valueOf(ruolo.toUpperCase());
        String encodedPassword = passwordEncoder.encode(password);

        System.out.println("Squadra ID: " + squadraId);
        Squadra squadra = null;
        if (squadraId != null) {
            squadra = squadraService.findById(squadraId);
            System.out.println("Squadra: " + squadra);
            if (squadra == null) {
                model.addAttribute("error", "Squadra non trovata.");
                return "register";
            }
        }

        if (credenzialiService.existsByUsername(username)) {
            model.addAttribute("esistente", "Username già esistente");
            return "register";
        }

        try {
            Credenziali credenziali = new Credenziali(username, encodedPassword, ruoloEnum);
            credenzialiService.save(credenziali);

            if (ruoloEnum == Credenziali.Ruolo.GIOCATORE) {
                Giocatore giocatore = new Giocatore();
                giocatore.setNome(nome);
                giocatore.setCognome(cognome);
                giocatore.setDataNascita(dataNascita);
                giocatore.setLuogoNascita(luogoNascita);
                giocatore.setInizioTesseramento(inizioTesseramento);
                giocatore.setFineTesseramento(fineTesseramento);
                giocatore.setSquadra(squadra);
                giocatore.setRuolo(ruoloGiocatore);
                giocatore.setCredenziali(credenziali);
                giocatoreService.save(giocatore);
            } else if (ruoloEnum == Credenziali.Ruolo.PRESIDENTE) {
                Presidente presidente = new Presidente();
                presidente.setNome(nome);
                presidente.setCognome(cognome);
                presidente.setCodiceFiscale(codiceFiscale);
                presidente.setDataNascita(dataNascita);
                presidente.setLuogoNascita(luogoNascita);
                presidente.setCredenziali(credenziali);
                presidenteService.save(presidente);
            }

            redirectAttributes.addFlashAttribute("success", "Credenziali salvate con successo!");

            try {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
                Authentication authentication = authenticationManager.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("Autenticazione avvenuta con successo per: " + authentication.getName());
                model.addAttribute("registrato", "Registrazione Completata. Esegui il login per accedere alle risorse");
                return "login";
            } catch (BadCredentialsException e) {
                System.out.println("Errore di autenticazione: " + e.getMessage());
                model.addAttribute("error", "Credenziali errate.");
                return "redirect:/";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Errore durante la registrazione. Riprova.");
            return "register";
        }
    }
}