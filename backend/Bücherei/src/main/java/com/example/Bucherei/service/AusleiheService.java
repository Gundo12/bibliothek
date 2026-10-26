package com.example.Bucherei.service;

import com.example.Bucherei.model.*;
import com.example.Bucherei.repository.AusleiheRepository;
import com.example.Bucherei.repository.MediumRepository;
import com.example.Bucherei.repository.NutzerRepository;
import com.example.Bucherei.repository.ReservierungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**Add commentMore actions


 * Serviceklasse für die Verwaltung von Ausleihen innerhalb der Bibliotheksanwendung.
 * <p>
 * Diese Klasse kapselt die Geschäftslogik rund um das Ausleihen und Verlängern von Medien.
 * Sie verwaltet dabei die Beziehungen zwischen Nutzern, Medien, Reservierungen und Ausleihen
 * und stellt sicher, dass die Status korrekt aktualisiert werden.
 */
@Service
public class AusleiheService {

    @Autowired
    private AusleiheRepository ausleiheRepository;

    @Autowired
    private NutzerRepository nutzerRepository;

    @Autowired
    private MediumRepository mediumRepository;

    @Autowired
    private ReservierungRepository reservierungRepository;

    /**
     Add comment
     More actions
     * Erstellt eine neue Ausleihe für einen Nutzer und ein bestimmtes Medium.
     * <p>
     * Wenn für das Medium bereits eine offene Reservierung besteht, wird diese auf „ABGEHOLT“ gesetzt.
     * Der Status des Mediums wird auf „AUSGELIEHEN“ aktualisiert.
     * @param nutzerId Die ID des ausleihenden Nutzers
     * @param mediumId Die ID des Mediums, das ausgeliehen werden soll
     * @param tage     Die Ausleihdauer in Tagen
     * @return die erstellte und gespeicherte Ausleihe
     * @throws IllegalArgumentException wenn der Nutzer oder das Medium nicht gefunden wird
     * @throws IllegalStateException    wenn das Medium nicht verfügbar ist
     */
    public Ausleihe ausleihen(Long nutzerId, Long mediumId, int tage) {
        Nutzer nutzer = nutzerRepository.findById(nutzerId)
                .orElseThrow(() -> new IllegalArgumentException("Nutzer nicht gefunden"));
        Medium medium = mediumRepository.findById(mediumId)
                .orElseThrow(() -> new IllegalArgumentException("Medium nicht gefunden"));

        if (medium.getStatus() != MediumStatus.VERFUEGBAR && medium.getStatus() != MediumStatus.RESERVIERT) {
            throw new IllegalStateException("Medium ist nicht verfügbar.");
        }

        /**


         * Gibt alle Ausleihen eines bestimmten Nutzers zurück.Add commentMore actions


         *


         * @param nutzerId die ID des Nutzers


         * @return Liste aller Ausleihen, die dem Nutzer zugeordnet sind


         */
        // Prüfen, ob eine Reservierung vorhanden ist
        List<Reservierung> offeneReservierungen = reservierungRepository
                .findByMediumMediumIdAndStatus(mediumId, ReservierungsStatus.OFFEN);

        Reservierung zugeordneteReservierung = null;
        if (!offeneReservierungen.isEmpty()) {
            zugeordneteReservierung = offeneReservierungen.get(0);
            zugeordneteReservierung.setStatus(ReservierungsStatus.ABGEHOLT);
            reservierungRepository.save(zugeordneteReservierung);
        }

        medium.setStatus(MediumStatus.AUSGELIEHEN);
        mediumRepository.save(medium);

        Ausleihe ausleihe = new Ausleihe(nutzer, medium, tage);
        ausleihe.setReservierung(zugeordneteReservierung);
        return ausleiheRepository.save(ausleihe);
    }

    /**Add commentMore actions


     * Gibt alle Ausleihen eines bestimmten Nutzers zurück.


     *


     * @param nutzerId die ID des Nutzers


     * @return Liste aller Ausleihen, die dem Nutzer zugeordnet sind


     */
    public List<Ausleihe> findeAlleFuerNutzer(Long nutzerId) {
        return ausleiheRepository.findByNutzerNutzerId(nutzerId);
    }

    /**


     * Verlängert eine bestehende Ausleihe, sofern das neue Rückgabedatum nach dem aktuellen liegt.


     * Der Status der Ausleihe wird auf „VERLAENGERT“ gesetzt.Add commentMore actions


     *


     * @param ausleiheId         die ID der Ausleihe, die verlängert werden soll


     * @param neuesRueckgabedatum das neue Rückgabedatum


     * @return die aktualisierte Ausleihe


     * @throws IllegalArgumentException wenn die Ausleihe nicht gefunden wird oder das neue Rückgabedatum ungültig ist


     */
    public Ausleihe verlaengern(Long ausleiheId, LocalDate neuesRueckgabedatum) {
        Ausleihe ausleihe = ausleiheRepository.findById(ausleiheId)
                .orElseThrow(() -> new IllegalArgumentException("Ausleihe nicht gefunden"));

        if (neuesRueckgabedatum != null && neuesRueckgabedatum.isAfter(ausleihe.getRueckgabedatum())) {
            ausleihe.setRueckgabedatum(neuesRueckgabedatum);
            ausleihe.setStatus(AusleihStatus.VERLAENGERT);
        } else {
            throw new IllegalArgumentException("Neues Rückgabedatum muss nach dem aktuellen liegen.");
        }

        return ausleiheRepository.save(ausleihe);
    }
}

