package com.example.Bucherei.model;

import jakarta.persistence.*;

@Entity
public class Standort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Stockwerk stockwerk;          // z. B. "1.OG",
    private String regal;         // z. B. "Regal A4"
    private String fach;          // z. B. "Fach 3"

    public Standort() {}

    public Standort(Stockwerk stockwerk, String regal, String fach) {
        this.stockwerk = stockwerk;
        this.regal = regal;
        this.fach = fach;
    }

    // Getter + Setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stockwerk getStockwerk() {
        return stockwerk;
    }

    public void setStockwerk(Stockwerk stockwerk) {
        this.stockwerk = stockwerk;
    }

    public String getRegal() {
        return regal;
    }

    public void setRegal(String regal) {
        this.regal = regal;
    }

    public String getFach() {
        return fach;
    }

    public void setFach(String fach) {
        this.fach = fach;
    }
}

