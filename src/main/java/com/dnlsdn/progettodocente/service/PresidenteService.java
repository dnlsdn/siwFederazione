package com.dnlsdn.progettodocente.service;

import com.dnlsdn.progettodocente.model.Presidente;
import com.dnlsdn.progettodocente.repository.PresidenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresidenteService {

    @Autowired
    private PresidenteRepository presidenteRepository;

    public Presidente save(Presidente presidente) {
        return this.presidenteRepository.save(presidente);
    }

    public Presidente findByCredenzialiUsername(String username) {
        return this.presidenteRepository.findByCredenzialiUsername(username);
    }

    public Presidente findByCognome(String cognome) {
        return this.presidenteRepository.findByCognome(cognome);
    }

    public Presidente findById(Long id) {
        return this.presidenteRepository.findById(id).get();
    }
}
