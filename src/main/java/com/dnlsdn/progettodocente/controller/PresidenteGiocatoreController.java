package com.dnlsdn.progettodocente.controller;

import com.dnlsdn.progettodocente.configuration.AuthConfiguration;
import com.dnlsdn.progettodocente.model.Giocatore;
import com.dnlsdn.progettodocente.model.Presidente;
import com.dnlsdn.progettodocente.model.Squadra;
import com.dnlsdn.progettodocente.service.GiocatoreService;
import com.dnlsdn.progettodocente.service.PresidenteService;
import com.dnlsdn.progettodocente.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/presidente")
public class PresidenteGiocatoreController {

    @Autowired
    private GiocatoreService giocatoreService;
    @Autowired
    private SquadraService squadraService;
    @Autowired
    private PresidenteService presidenteService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthConfiguration authConfiguration;

    @GetMapping("/gestioneGiocatore")
    public String gestioneGiocatore(@RequestParam(value = "cognome", required = false) String cognome, @RequestParam(value = "username") String username, Model model) {
        List<Giocatore> giocatori = giocatoreService.findAll();
        Presidente presidente = presidenteService.findByCredenzialiUsername(username);
        if (cognome != null) {
            List<Giocatore> listaNuova = new ArrayList<>();
            for (Giocatore giocatore : giocatori) {
                if (giocatore.getCognome().equals(cognome)) {
                    listaNuova.add(giocatore);
                }
            }
            if (listaNuova.isEmpty()) {
                model.addAttribute("nessunGiocatore", "Non è stato trovato alcun giocatore");
                model.addAttribute("presidente", presidente);
                return "gestioneGiocatore";
            }

            giocatori.clear();
            giocatori.addAll(listaNuova);
        }

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("cognome", cognome);
        model.addAttribute("presidente", presidente);
        return "gestioneGiocatore";
    }

    @PostMapping("/eliminaSquadraGiocatore/{idGiocatore}/{idPresidente}")
    public String eliminaSquadraGiocatore(@PathVariable Long idGiocatore, @PathVariable Long idPresidente, Model model) {
        Giocatore giocatore = giocatoreService.findById(idGiocatore);
        Presidente presidente = presidenteService.findById(idPresidente);

        if (!giocatore.getSquadra().getPresidente().equals(presidente)) {
            model.addAttribute("presidenteDiverso", "Non sei il presidente della squadra del giocatore!");
            model.addAttribute("giocatori", giocatoreService.findAll());
            model.addAttribute("presidente", presidenteService.findById(idPresidente));
            return "gestioneGiocatore";
        }

        if (giocatore.getSquadra() == null) {
            model.addAttribute("nessunaSquadra", "Il Giocatore non appartiene ad alcuna squadra");
            model.addAttribute("giocatori", giocatoreService.findAll());
            model.addAttribute("presidente", presidenteService.findById(idPresidente));
            return "gestioneGiocatore";
        }
        String nomeSquadra = giocatore.getSquadra().getNome();

        giocatore.setSquadra(null);
        giocatoreService.save(giocatore);
        String eliminazioneSquadra = "Fine periodo nella squadra " + nomeSquadra;
        model.addAttribute("eliminazioneSquadra", eliminazioneSquadra);
        model.addAttribute("giocatori", giocatoreService.findAll());
        model.addAttribute("presidente", presidenteService.findById(idPresidente));
        return "gestioneGiocatore";
    }

    @PostMapping("/inserisciSquadra/{id}")
    public String inserisciSquadra(@PathVariable Long id, Model model, @RequestParam("nomeSquadra") String nomeSquadra, @RequestParam("idPresidente") Long idPresidente) {
        Squadra squadra = squadraService.findByNome(nomeSquadra);
        Giocatore giocatore = giocatoreService.findById(id);
        if (squadra == null) {
            model.addAttribute("nessunaSquadraNome", "Nessuna Squadra trovata con questo nome");
            model.addAttribute("giocatori", giocatoreService.findAll());
            model.addAttribute("presidente", presidenteService.findById(idPresidente));
            return "gestioneGiocatore";
        }
        if (giocatore.getSquadra() != null) {
            model.addAttribute("isSquadra", "Il Giocatore fa già parte di una squadra");
            model.addAttribute("giocatori", giocatoreService.findAll());
            model.addAttribute("presidente", presidenteService.findById(idPresidente));
            return "gestioneGiocatore";
        }
        if (!squadra.getPresidente().equals(presidenteService.findById(idPresidente))) {
            model.addAttribute("noPres", "Non è possibile aggiungere un giocatore ad una squadra di cui non si è presidente!");
            model.addAttribute("giocatori", giocatoreService.findAll());
            model.addAttribute("presidente", presidenteService.findById(idPresidente));
            return "gestioneGiocatore";
        }
        giocatore.setSquadra(squadra);
        giocatoreService.save(giocatore);
        model.addAttribute("aggiuntaSquadra", "Il Giocatore è stato inserito nella squadra " + nomeSquadra);
        model.addAttribute("giocatori", giocatoreService.findAll());
        model.addAttribute("presidente", presidenteService.findById(idPresidente));
        return "gestioneGiocatore";
    }
}
