package com.dnlsdn.progettodocente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    //    @Min(1908)
//    @Max(2024)
    private LocalDate dataNascita;
    @NotBlank
    private String luogoNascita;
    @NotBlank
    private String ruolo;

    private LocalDate inizioTesseramento;
    private LocalDate fineTesseramento;

    @ManyToOne
    private Squadra squadra;

    @OneToOne
    private Credenziali credenziali;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public LocalDate getInizioTesseramento() {
        return inizioTesseramento;
    }

    public void setInizioTesseramento(LocalDate inizioTesseramento) {
        this.inizioTesseramento = inizioTesseramento;
    }

    public LocalDate getFineTesseramento() {
        return fineTesseramento;
    }

    public void setFineTesseramento(LocalDate fineTesseramento) {
        this.fineTesseramento = fineTesseramento;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public Credenziali getCredenziali() {
        return credenziali;
    }

    public void setCredenziali(Credenziali credenziali) {
        this.credenziali = credenziali;
    }
}
