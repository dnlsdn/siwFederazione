package com.dnlsdn.progettodocente.service;

import com.dnlsdn.progettodocente.model.Credenziali;
import com.dnlsdn.progettodocente.repository.CredenzialiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredenzialiService {

    @Autowired
    private CredenzialiRepository credenzialiRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public Credenziali save(Credenziali credenziali) {
        //Credenziali nuovo = new Credenziali(credenziali.getUsername(), passwordEncoder.encode(credenziali.getPassword()), credenziali.getRuolo());
        return this.credenzialiRepository.save(credenziali);
    }
    
    public boolean existsByUsername(String username) {
        return this.credenzialiRepository.existsByUsername(username);
    }
}
