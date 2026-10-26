package com.example.Bucherei.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "buchId")
public class Buch extends Medium {


    private String isbn;
    private int seitenzahl;

    @ManyToOne
    private Standort standort;

    public Standort getStandort() {
        return standort;
    }

    public void setStandort(Standort standort) {
        this.standort = standort;
    }

    public int getSeitenzahl() {
        return seitenzahl;
    }

    public void setSeitenzahl(int seitenzahl) {
        this.seitenzahl = seitenzahl;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Buch(String titel, String autor, String kategorie, String bildLink, MediumStatus mediumStatus, MediumTyp mediumTyp, String isbn, int seitenzahl) {
        super(titel, autor, kategorie, bildLink, mediumStatus, mediumTyp);
        this.isbn = isbn;
        this.seitenzahl = seitenzahl;
    }

    public Buch(){

    }
}
