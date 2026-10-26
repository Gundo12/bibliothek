package com.example.Bucherei.service;

import com.example.Bucherei.model.Nutzer;
import com.example.Bucherei.repository.NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


/**
 * Serviceklasse für die Verwaltung von Nutzern innerhalb der Bibliotheksanwendung.
 * <p>
 * Diese Klasse stellt zentrale Funktionen zur Registrierung und Identifikation von Nutzern bereit.
 * Sie übernimmt Validierungslogik und die Interaktion mit der Datenbank über das NutzerRepository
 */
@Service
public class NutzerService {

    private final NutzerRepository nutzerRepository;

    /**
     * Konstruktorinjektion des NutzerRepository
     *
     * @param nutzerRepository das Repository zur Datenbankkommunikation für Nutzer
     */
    @Autowired
    public NutzerService(NutzerRepository nutzerRepository) {
        this.nutzerRepository = nutzerRepository;
    }

    /**
     * Registriert einen neuen Nutzer in der Anwendung.
     * Es wird geprüft, ob die E-Mail-Adresse bereits vergeben ist.
     * Das Registrierungsdatum wird automatisch auf das aktuelle Datum gesetzt.Add commentMore actions
     *
     * @param nutzer das zu registrierende Nutzerobjekt
     * @return der gespeicherte Nutzer
     * @throws IllegalArgumentException wenn bereits ein Nutzer mit dieser E-Mail existiert
     */
    public Nutzer registrieren(Nutzer nutzer) {
        if (nutzerRepository.findByEmail(nutzer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ein Nutzer mit dieser E-Mail existiert bereits.");
        }

        nutzer.setRegistrierungsdatum(LocalDate.now());
        return nutzerRepository.save(nutzer);
    }


    /**
     * Sucht einen Nutzer anhand seiner ID.
     *
     * @param id die ID des gesuchten Nutzers
     * @return der gefundene Nutzer
     * @throws IllegalArgumentException wenn kein Nutzer mit dieser ID existiert
     */
    public Nutzer findeNutzerNachId(Long id) {
        return nutzerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nutzer nicht gefunden"));
    }
}
