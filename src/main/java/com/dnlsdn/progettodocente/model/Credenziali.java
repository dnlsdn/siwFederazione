package com.dnlsdn.progettodocente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Credenziali {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;
    @OneToOne(mappedBy = "credenziali")
    private Giocatore giocatore;
    @OneToOne(mappedBy = "credenziali")
    private Presidente presidente;

    public Credenziali(String username, String password, Ruolo ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Credenziali() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public enum Ruolo {
        GIOCATORE,
        PRESIDENTE,
        AMMINISTRATORE
    }
}
