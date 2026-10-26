package com.example.Bucherei.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Reservierung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Nutzer nutzer;

    @ManyToOne
    private Medium medium;

    private LocalDate reservierungsdatum;

    @Enumerated(EnumType.STRING)
    private ReservierungsStatus status;

    private LocalDateTime reserviertBis;


    public Reservierung() {
        // leer für JPA
    }

    public Reservierung(Nutzer nutzer, Medium medium) {
        this.nutzer = nutzer;
        this.medium = medium;
        this.reserviertBis = LocalDateTime.now().plusMinutes(3880);
        this.reservierungsdatum = LocalDate.now();
        this.status = ReservierungsStatus.OFFEN;
    }

    // Getter & Setter …

    public Long getId() { return id; }

    public Nutzer getNutzer() { return nutzer; }

    public Medium getMedium() { return medium; }

    public LocalDate getReservierungsdatum() { return reservierungsdatum; }

    public ReservierungsStatus getStatus() { return status; }

    public void setStatus(ReservierungsStatus status) { this.status = status; }

    public LocalDateTime getReserviertBis() {
        return reserviertBis;
    }

    public void setReserviertBis(LocalDateTime reserviertBis) {
        this.reserviertBis = reserviertBis;
    }

}
