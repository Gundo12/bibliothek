package com.example.Bucherei.service;

import com.example.Bucherei.model.*;
import com.example.Bucherei.repository.MediumRepository;
import com.example.Bucherei.repository.NutzerRepository;
import com.example.Bucherei.repository.ReservierungRepository;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import java.util.List;
import java.util.Optional;


/**
 * Serviceklasse zur Verwaltung von Reservierungen in der Bibliotheksanwendung.Add commentMore actions
 * <p>
 * Diese Klasse kapselt die Geschäftslogik für das Erstellen, Stornieren und Abfragen von Reservierungen.
 * Dabei wird sichergestellt, dass der Status von Medien und Reservierungen korrekt angepasst wird.
 */
@Service
public class ReservierungService {

    private final ReservierungRepository reservierungRepository;
    private final NutzerRepository nutzerRepository;
    private final MediumRepository mediumRepository;

    /**
     * Konstruktorinjektion der benötigten Repositories.
     *
     * @param rr Reservierungs-Repository
     * @param nr Nutzer-RepositoryAdd commentMore actions
     * @param mr Medium-Repository
     */
    @Autowired
    public ReservierungService(ReservierungRepository rr, NutzerRepository nr, MediumRepository mr) {
        this.reservierungRepository = rr;
        this.nutzerRepository = nr;
        this.mediumRepository = mr;
    }

    /**
     * Erstellt eine neue Reservierung für ein Medium durch einen Nutzer.
     * <p>
     * Das Medium muss verfügbar sein, um reserviert werden zu können.
     * Der Status des Mediums wird nach erfolgreicher Reservierung auf "RESERVIERT" gesetzt.
     *
     * @param nutzerId ID des reservierenden NutzersAdd commentMore actions
     * @param mediumId ID des zu reservierenden Mediums
     * @return die gespeicherte Reservierung
     * @throws IllegalArgumentException wenn Nutzer oder Medium nicht existieren
     * @throws IllegalStateException    wenn das Medium nicht verfügbar ist
     */
    public Reservierung reservieren(Long nutzerId, Long mediumId) {
        Nutzer nutzer = nutzerRepository.findById(nutzerId)
                .orElseThrow(() -> new IllegalArgumentException("Nutzer nicht gefunden"));
        Medium medium = mediumRepository.findById(mediumId)
                .orElseThrow(() -> new IllegalArgumentException("Medium nicht gefunden"));

        if (medium.getStatus() != MediumStatus.VERFUEGBAR) {
            throw new IllegalStateException("Medium ist nicht verfügbar");
        }

        medium.setStatus(MediumStatus.RESERVIERT);
        mediumRepository.save(medium);

        Reservierung reservierung = new Reservierung(nutzer, medium);
        return reservierungRepository.save(reservierung);
    }

    /**
     * Gibt alle Reservierungen zurück, die einem bestimmten Nutzer zugeordnet sind.
     *
     * @param nutzerId ID des Nutzers
     * @return Liste der Reservierungen
     */
    public List<Reservierung> findeAlleFuerNutzer(Long nutzerId) {
        return reservierungRepository.findByNutzerNutzerId(nutzerId);
    }

    private static final Logger logger = LoggerFactory.getLogger(ReservierungService.class);

    /**
     * Sucht nach einer Reservierung basierend auf der ID eines Mediums.
     * <p>
     * Diese Methode nutzt Logging zur Laufzeitüberwachung.
     * @param mediumId ID des Mediums
     * @return Optional mit der gefundenen Reservierung oder leer
     */
    public Optional<Reservierung> getReservierungByMediumId(Long mediumId) {
        logger.info("Suche Reservierung für Medium-ID: {}", mediumId);
        return reservierungRepository.findByMediumMediumId(mediumId);
    }


    /**
     * Storniert eine offene Reservierung.
     * <p>
     * Der Status der Reservierung wird auf "STORNIERT" gesetzt und das zugehörige Medium
     * wird wieder auf "VERFUEGBAR" gesetzt.
     *
     * @param reservierungsId ID der zu stornierenden Reservierung
     * @return die aktualisierte Reservierung
     * @throws IllegalArgumentException wenn die Reservierung nicht gefunden wurde
     * @throws IllegalStateException    wenn die Reservierung nicht mehr offen ist
     */
    public Reservierung storniereReservierung(Long reservierungsId) {
        Reservierung reservierung = reservierungRepository.findById(reservierungsId)
                .orElseThrow(() -> new IllegalArgumentException("Reservierung nicht gefunden"));

        if (reservierung.getStatus() != ReservierungsStatus.OFFEN) {
            throw new IllegalStateException("Reservierung ist bereits abgeschlossen oder storniert");
        }

        reservierung.setStatus(ReservierungsStatus.STORNIERT);

        Medium medium = reservierung.getMedium();
        medium.setStatus(MediumStatus.VERFUEGBAR);
        mediumRepository.save(medium);

        return reservierungRepository.save(reservierung);
    }

}
