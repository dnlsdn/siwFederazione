package com.dnlsdn.progettodocente.repository;

import com.dnlsdn.progettodocente.model.Credenziali;
import org.springframework.data.repository.CrudRepository;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
    boolean existsByUsername(String username);
}
