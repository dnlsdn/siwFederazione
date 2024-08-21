package com.dnlsdn.progettodocente.controller;

import com.dnlsdn.progettodocente.model.Squadra;
import com.dnlsdn.progettodocente.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListaSquadreController {

    @Autowired
    private SquadraService squadraService;

    @GetMapping("/listaSquadre")
    public String listaSquadre(Model model) {
        List<Squadra> squadre = squadraService.findAll();
        System.out.println(squadre);
        model.addAttribute("squadre", squadre);
        return "listaSquadre";
    }
}
