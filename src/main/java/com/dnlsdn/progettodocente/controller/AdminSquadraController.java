package com.dnlsdn.progettodocente.controller;

import com.dnlsdn.progettodocente.model.Presidente;
import com.dnlsdn.progettodocente.model.Squadra;
import com.dnlsdn.progettodocente.service.PresidenteService;
import com.dnlsdn.progettodocente.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminSquadraController {

    @Autowired
    private SquadraService squadraService;
    @Autowired
    private PresidenteService presidenteService;

    @GetMapping("/gestioneSquadra")
    public String gestioneSquadra(@RequestParam(value = "nome", required = false) String nome, Model model) {
        List<Squadra> squadre = squadraService.findAll();
        if (nome != null) {
            squadre.clear();
            Squadra squadra = squadraService.findByNome(nome);
            if (squadra == null) {
                model.addAttribute("nessunaSquadra", "Non è stata trovata alcuna squadra");
                return "gestioneSquadra";
            }
            squadre.add(squadra);
        }

        model.addAttribute("squadre", squadre);
        model.addAttribute("nome", nome);
        return "gestioneSquadra";
    }

    @GetMapping("/modificaSquadra/{id}")
    public String modificaSquadra(Model model, @PathVariable("id") Long id) {
        Squadra squadra = squadraService.findById(id);
        model.addAttribute("squadra", squadra);
        return "modificaSquadra";
    }

    @PostMapping("/aggiornaSquadra")
    public String aggiornaSquadra(@RequestParam("id") Long id,
                                  @RequestParam("indirizzo") String indirizzo,
                                  @RequestParam("annoFondazione") int annoFondazione,
                                  @RequestParam("presidente.cognome") String cognome,
                                  Model model) {
        Squadra squadra = squadraService.findById(id);
        Presidente nuovoPresidente = presidenteService.findByCognome(cognome);
        if (nuovoPresidente != null) {
            squadra.setIndirizzo(indirizzo);
            squadra.setAnnoFondazione(annoFondazione);
            squadra.setPresidente(nuovoPresidente);
        } else {
            model.addAttribute("errorePresidente", "Nessun Presidente trovato!");
            model.addAttribute("squadra", squadra);
            return "modificaSquadra";
        }

        squadraService.save(squadra);
        List<Squadra> squadre = squadraService.findAll();
        model.addAttribute("squadre", squadre);
        return "gestioneSquadra";
    }

    @GetMapping("/nuovaSquadra")
    public String nuovaSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "nuovaSquadra";
    }

    @PostMapping("/nuovaSquadra")
    public String inserisciSquadra(@RequestParam("nome") String nome,
                                   @RequestParam("indirizzo") String indirizzo,
                                   @RequestParam("annoFondazione") int annoFondazione,
                                   @RequestParam("presidenteCognome") String presidenteCognome,
                                   Model model) {
        Squadra squadra = new Squadra();

        if (nome.isEmpty() || indirizzo.isEmpty()) {
            model.addAttribute("erroreCampi", "Tutti i campi sono Obbligatori");
            model.addAttribute("squadra", squadra);
            return "nuovaSquadra";
        }

        squadra.setNome(nome);
        squadra.setIndirizzo(indirizzo);
        squadra.setAnnoFondazione(annoFondazione);

        Presidente presidente = presidenteService.findByCognome(presidenteCognome);
        if (presidente == null) {
            model.addAttribute("errorePresidente", "Non esiste alcun presidente con questo cognome");
            model.addAttribute("squadra", squadra);
            return "nuovaSquadra";
        }
        squadra.setPresidente(presidente);

        squadraService.save(squadra);
        List<Squadra> squadre = squadraService.findAll();
        model.addAttribute("squadre", squadre);
        return "gestioneSquadra";
    }
}