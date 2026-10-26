package com.example.Bucherei.service;

import com.example.Bucherei.model.Medium;
import com.example.Bucherei.model.MediumStatus;
import com.example.Bucherei.model.Reservierung;
import com.example.Bucherei.repository.MediumRepository;
import com.example.Bucherei.repository.ReservierungRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Serviceklasse zur automatisierten Bereinigung abgelaufener Reservierungen.Wird in der BuchereiApplication verwendet mit @Enable Scheduler
 * <p>
 * Dieser Dienst wird regelmäßig über einen Scheduled Task ausgeführt. Dabei werden alle Reservierungen,
 * deren Ablaufdatum überschritten ist, aus der Datenbank entfernt und die zugehörigen Medien wieder auf
 * den Status "VERFUEGBAR" gesetzt.
 * <p>
 * Die Implementierung dient als Grundlage für eine zukünftige Erweiterung um Fristenlogik und
 * automatische Benachrichtigungen.
 */
@Service
public class ReservierungCleanupService {

    private final ReservierungRepository reservierungRepository;
    private final MediumRepository mediumRepository;

    /**


     * Konstruktorinjektion der benötigten Repositories.
     *
     * @param reservierungRepository Repository für Reservierungsdaten
     * @param mediumRepository       Repository für Mediendaten
     */
    public ReservierungCleanupService(ReservierungRepository reservierungRepository, MediumRepository mediumRepository) {
        this.reservierungRepository = reservierungRepository;
        this.mediumRepository = mediumRepository;
    }

    /**


     * Geplante Methode, die regelmäßig nach einem festen Intervall (alle 60 Sekunden) ausgeführt wird.
     * <p>
     * Alle abgelaufenen Reservierungen, deren „reserviert bis“-Datum in der Vergangenheit liegt,
     * werden aus der Datenbank gelöscht. Die betroffenen Medien erhalten wieder den Status „VERFUEGBAR“.
     */
    @Scheduled(fixedRate = 60000)
    public void removeExpiredReservations() {
        List<Reservierung> abgelaufene = reservierungRepository.findByReserviertBisBefore(LocalDateTime.now());

        for (Reservierung reservierung : abgelaufene) {
            Medium medium = reservierung.getMedium();
            medium.setStatus(MediumStatus.VERFUEGBAR);
            mediumRepository.save(medium);
        }

        reservierungRepository.deleteAll(abgelaufene);
    }

}
