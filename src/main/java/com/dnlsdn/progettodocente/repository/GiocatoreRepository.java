package com.dnlsdn.progettodocente.repository;

import com.dnlsdn.progettodocente.model.Giocatore;
import org.springframework.data.repository.CrudRepository;

public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {
    public Giocatore findByCredenzialiUsername(String username);

    public Giocatore findByCognome(String cognome);
}
