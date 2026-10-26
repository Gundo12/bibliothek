package com.example.Bucherei.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Ausleihe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Nutzer nutzer;

    @ManyToOne
    private Medium medium;

    private LocalDate ausleihdatum;

    private LocalDate rueckgabedatum;

    @Enumerated(EnumType.STRING)
    private AusleihStatus status;

    @OneToOne
    private Reservierung reservierung; // Verknüpfung zur Reservierung

    public Ausleihe() {}

    public Ausleihe(Nutzer nutzer, Medium medium, int tage) {
        this.nutzer = nutzer;
        this.medium = medium;
        this.ausleihdatum = LocalDate.now();
        this.rueckgabedatum = ausleihdatum.plusDays(tage);
        this.status = AusleihStatus.AKTIV;
    }

    // Getter & Setter (auch für reservierung)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Nutzer getNutzer() { return nutzer; }
    public void setNutzer(Nutzer nutzer) { this.nutzer = nutzer; }

    public Medium getMedium() { return medium; }
    public void setMedium(Medium medium) { this.medium = medium; }

    public LocalDate getAusleihdatum() { return ausleihdatum; }
    public void setAusleihdatum(LocalDate ausleihdatum) { this.ausleihdatum = ausleihdatum; }

    public LocalDate getRueckgabedatum() { return rueckgabedatum; }
    public void setRueckgabedatum(LocalDate rueckgabedatum) { this.rueckgabedatum = rueckgabedatum; }

    public AusleihStatus getStatus() { return status; }
    public void setStatus(AusleihStatus status) { this.status = status; }

    public Reservierung getReservierung() { return reservierung; }
    public void setReservierung(Reservierung reservierung) { this.reservierung = reservierung; }
}

