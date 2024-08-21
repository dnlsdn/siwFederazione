package com.dnlsdn.progettodocente.service;

import com.dnlsdn.progettodocente.model.Squadra;
import com.dnlsdn.progettodocente.repository.SquadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SquadraService {

    @Autowired
    private SquadraRepository squadraRepository;

    public Squadra save(Squadra squadra) {
        return this.squadraRepository.save(squadra);
    }

    public List<Squadra> findAll() {
        return (List<Squadra>) this.squadraRepository.findAll();
    }

    public Squadra findById(Long squadraId) {
        return this.squadraRepository.findById(squadraId).get();
    }

    public List<Squadra> findByNome(String nome) {
        return this.squadraRepository.findByNome(nome);
    }
}
