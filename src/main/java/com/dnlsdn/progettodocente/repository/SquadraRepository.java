package com.dnlsdn.progettodocente.repository;

import com.dnlsdn.progettodocente.model.Squadra;
import org.springframework.data.repository.CrudRepository;

public interface SquadraRepository extends CrudRepository<Squadra, Long> {


    Squadra findByNome(String nome);
}
