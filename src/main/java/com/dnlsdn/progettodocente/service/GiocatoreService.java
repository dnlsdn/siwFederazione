package com.dnlsdn.progettodocente.service;

import com.dnlsdn.progettodocente.model.Giocatore;
import com.dnlsdn.progettodocente.repository.GiocatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiocatoreService {

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    public Giocatore save(Giocatore giocatore) {
        return this.giocatoreRepository.save(giocatore);
    }

    public void deleteById(Long id) {
        this.giocatoreRepository.deleteById(id);
    }

    public Giocatore findById(Long id) {
        return this.giocatoreRepository.findById(id).get();
    }

    public Giocatore findByCredenzialiUsername(String username) {
        return this.giocatoreRepository.findByCredenzialiUsername(username);
    }

    public List<Giocatore> findAll() {
        return (List<Giocatore>) this.giocatoreRepository.findAll();
    }

    public Giocatore findByCognome(String cognome) {
        return this.giocatoreRepository.findByCognome(cognome);
    }
}
