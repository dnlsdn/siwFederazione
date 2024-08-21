package com.dnlsdn.progettodocente.controller;

import com.dnlsdn.progettodocente.model.Giocatore;
import com.dnlsdn.progettodocente.service.GiocatoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/presidente/giocatore")
public class PresidenteGiocatoreController {

    @Autowired
    private GiocatoreService giocatoreService;

    // Mostra il form per aggiungere un nuovo giocatore
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("giocatore", new Giocatore());
        return "giocatore/add";
    }

    // Gestisce la richiesta per aggiungere un giocatore
    @PostMapping("/add")
    public String addGiocatore(@Valid @ModelAttribute Giocatore giocatore, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "giocatore/add";
        }
        giocatoreService.save(giocatore);
        return "redirect:/success";
    }

    // Gestisce la richiesta per rimuovere un giocatore
    @DeleteMapping("/remove/{id}")
    public String removeGiocatore(@PathVariable Long id) {
        giocatoreService.deleteById(id);
        return "redirect:/success";
    }

    // Mostra il form per modificare un giocatore esistente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Giocatore giocatore = giocatoreService.findById(id);
        if (giocatore == null) {
            return "redirect:/error"; // o qualsiasi pagina di errore
        }
        model.addAttribute("giocatore", giocatore);
        return "giocatore/edit";
    }

    // Gestisce la richiesta per aggiornare un giocatore esistente
    @PostMapping("/edit/{id}")
    public String editGiocatore(@PathVariable Long id, @Valid @ModelAttribute Giocatore giocatore, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "giocatore/edit";
        }
        giocatore.setId(id);
        giocatoreService.save(giocatore);
        return "redirect:/success";
    }
}
