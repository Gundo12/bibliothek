package com.example.Bucherei.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nutzer")
public class Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nutzerId;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwort;

    private String adresse;

    private LocalDate geburtsdatum;

    private LocalDate registrierungsdatum;

    private String telefonnummer; // optional

    // Konstruktoren

    public Nutzer() {
        // Leerer Konstruktor für JPA
    }

    public Nutzer(String name, String email, String passwort, String adresse, LocalDate geburtsdatum, String telefonnummer) {
        this.name = name;
        this.email = email;
        this.passwort = passwort;
        this.adresse = adresse;
        this.geburtsdatum = geburtsdatum;
        this.registrierungsdatum = LocalDate.now();
        this.telefonnummer = telefonnummer;
    }

    // Getter & Setter

    public Long getNutzerId() {
        return nutzerId;
    }

    public void setNutzerId(Long nutzerId) {
        this.nutzerId = nutzerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public LocalDate getRegistrierungsdatum() {
        return registrierungsdatum;
    }

    public void setRegistrierungsdatum(LocalDate registrierungsdatum) {
        this.registrierungsdatum = registrierungsdatum;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
}
