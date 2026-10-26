package com.example.Bucherei.service;

import com.example.Bucherei.model.Medium;
import com.example.Bucherei.repository.MediumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Serviceklasse zur Verwaltung von Medien innerhalb der Bibliotheksanwendung
 * <p>
 * Diese Klasse stellt Funktionen zum Speichern, Suchen und Abrufen von Medien bereit
 * und bildet die zentrale Schnittstelle zwischen dem Controller und dem MediumRepository.
 */
@Service
public class MediumService {

    private final MediumRepository mediumRepository;

    /**
     * Konstruktorinjektion des MediumRepository.
     *
     * @param mediumRepository das Repository zur Datenbankkommunikation für Medien
     */
    @Autowired
    public MediumService(MediumRepository mediumRepository) {
        this.mediumRepository = mediumRepository;
    }

    /**


     * Speichert ein Medium in der Datenbank.
     * @param medium das zu speichernde Medium
     * @return das gespeicherte Medium
     */
    public Medium speichern(Medium medium) {
        return mediumRepository.save(medium);
    }

    /**
     * Gibt eine Liste aller in der Datenbank gespeicherten Medien zurück.
     *
     * @return Liste aller Medien
     */
    public List<Medium> findeAlle() {
        return mediumRepository.findAll();
    }


    /**
     * Sucht ein Medium anhand seiner ID.
     *
     * @param id die ID des Mediums
     * @return das gefundene Medium
     * @throws IllegalArgumentException wenn kein Medium mit dieser ID existiert
     */
    public Medium findeNachId(Long id) {
        return mediumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medium nicht gefunden"));
    }

    /**
     * Sucht Medien, deren Titel oder Autor den angegebenen Begriff enthalten.
     * Die Suche ist nicht case-sensitiv.
     *
     * @param begriff der Suchbegriff für Titel oder Autor
     * @return Liste der passenden Medien
     */
    public List<Medium> suche(String begriff) {
        return mediumRepository.findByTitelContainingIgnoreCaseOrAutorContainingIgnoreCase(begriff, begriff);
    }
}
