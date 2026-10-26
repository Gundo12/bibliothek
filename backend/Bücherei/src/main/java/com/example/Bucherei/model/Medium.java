package com.example.Bucherei.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "medium")
public abstract class Medium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediumId;

    private String titel;

    private String autor;

    private String kategorie;

    private String bildLink;

    @Enumerated(EnumType.STRING)
    private MediumStatus status;

    @Enumerated(EnumType.STRING)
    private MediumTyp mediumTyp;

    // Konstruktoren

    public Medium() {
        this.status = MediumStatus.VERFUEGBAR;
    }

    public Medium(String titel, String autor, String kategorie, String bildLink, MediumStatus mediumStatus, MediumTyp mediumTyp) {
        this.titel = titel;
        this.autor = autor;
        this.kategorie = kategorie;
        this.bildLink = bildLink;
        this.status = mediumStatus;
        this.mediumTyp = mediumTyp;
    }

    // Getter & Setter

    @JsonProperty("mediumTyp")
    public String getMediaType() {
        return this.getClass().getSimpleName().toUpperCase(); // z. B. "BUCH", "MAGAZIN"
    }

    public String getBildLink() {
        return bildLink;
    }

    public void setBildLink(String bildLink) {
        this.bildLink = bildLink;
    }


    public Long getMediumId() {
        return mediumId;
    }

    public void setMediumId(Long mediumId) {
        this.mediumId = mediumId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public MediumStatus getStatus() {
        return status;
    }

    public void setStatus(MediumStatus status) {
        this.status = status;
    }
}