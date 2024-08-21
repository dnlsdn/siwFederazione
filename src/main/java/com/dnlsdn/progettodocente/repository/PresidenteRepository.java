package com.dnlsdn.progettodocente.repository;

import com.dnlsdn.progettodocente.model.Presidente;
import org.springframework.data.repository.CrudRepository;

public interface PresidenteRepository extends CrudRepository<Presidente, Long> {
    public Presidente findByCredenzialiUsername(String username);

    public Presidente findByCognome(String cognome);
}
