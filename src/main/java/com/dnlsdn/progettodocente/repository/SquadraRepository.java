package com.dnlsdn.progettodocente.repository;

import com.dnlsdn.progettodocente.model.Squadra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SquadraRepository extends CrudRepository<Squadra, Long> {
    public List<Squadra> findByNome(String nome);
}
