package com.dnlsdn.progettodocente.controller;

import com.dnlsdn.progettodocente.model.Giocatore;
import com.dnlsdn.progettodocente.model.Squadra;
import com.dnlsdn.progettodocente.service.GiocatoreService;
import com.dnlsdn.progettodocente.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/presidente")
public class PresidenteGiocatoreController {

    @Autowired
    private GiocatoreService giocatoreService;
    @Autowired
    private SquadraService squadraService;

    @GetMapping("/gestioneGiocatore")
    public String gestioneGiocatore(@RequestParam(value = "cognome", required = false) String cognome, Model model) {
        List<Giocatore> giocatori = giocatoreService.findAll();
        if (cognome != null) {
            giocatori.clear();
            Giocatore giocatore = giocatoreService.findByCognome(cognome);
            if (giocatore == null) {
                System.out.println("QUIIIIII");
                model.addAttribute("nessunGiocatore", "Non è stato trovato alcun giocatore");
                return "gestioneGiocatore";
            }
            giocatori.add(giocatore);
        }

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("cognome", cognome);
        return "gestioneGiocatore";
    }

    @PostMapping("/eliminaSquadraGiocatore/{id}")
    public String eliminaSquadraGiocatore(@PathVariable Long id, Model model) {
        Giocatore giocatore = giocatoreService.findById(id);
        if (giocatore.getSquadra() == null) {
            model.addAttribute("nessunaSquadra", "Il Giocatore non appartiene ad alcuna squadra");
            model.addAttribute("giocatori", giocatoreService.findAll());
            return "gestioneGiocatore";
        }
        String nomeSquadra = giocatore.getSquadra().getNome();

        giocatore.setSquadra(null);
        giocatoreService.save(giocatore);
        String eliminazioneSquadra = "Fine periodo nella squadra " + nomeSquadra;
        model.addAttribute("eliminazioneSquadra", eliminazioneSquadra);
        model.addAttribute("giocatori", giocatoreService.findAll());
        return "gestioneGiocatore";
    }

    @PostMapping("/inserisciSquadra/{id}")
    public String inserisciSquadra(@PathVariable Long id, Model model, @RequestParam("nomeSquadra") String nomeSquadra) {
        Squadra squadra = squadraService.findByNome(nomeSquadra);
        Giocatore giocatore = giocatoreService.findById(id);
        if (squadra == null) {
            model.addAttribute("nessunaSquadraNome", "Nessuna Squadra trovata con questo nome");
            model.addAttribute("giocatori", giocatoreService.findAll());
            return "gestioneGiocatore";
        }
        if (giocatore.getSquadra() != null) {
            model.addAttribute("isSquadra", "Il Giocatore fa già parte di una squadra");
            model.addAttribute("giocatori", giocatoreService.findAll());
            return "gestioneSquadra";
        }
        giocatore.setSquadra(squadra);
        giocatoreService.save(giocatore);
        model.addAttribute("aggiuntaSquadra", "Il Giocatore è stato inserito nella squadra " + nomeSquadra);
        model.addAttribute("giocatori", giocatoreService.findAll());
        return "gestioneGiocatore";
    }
}
