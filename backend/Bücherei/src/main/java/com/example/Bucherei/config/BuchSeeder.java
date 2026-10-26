package com.example.Bucherei.config;

import com.example.Bucherei.model.*;
import com.example.Bucherei.repository.MediumRepository;
import com.example.Bucherei.repository.NutzerRepository;
import com.example.Bucherei.repository.StandortRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

@Component
public class BuchSeeder implements CommandLineRunner {


    private final MediumRepository mediumRepository;
    private final Random random = new Random();
    private final NutzerRepository nutzerRepository; // NEU

    private final StandortRepository standortRepository;
    private final String demoUserPassword;



      public BuchSeeder(MediumRepository mediumRepository,
                        NutzerRepository nutzerRepository,
                        StandortRepository standortRepository,
                        @Value("${app.demo-user.password}") String demoUserPassword) {
        this.mediumRepository = mediumRepository;
        this.nutzerRepository = nutzerRepository;
        this.standortRepository = standortRepository;
        this.demoUserPassword = demoUserPassword;
    }

    @Override
    public void run(String... args) {
        Faker faker = new Faker(new Locale("de"));

        this.generiereNutzer();



        for (int i = 0; i < 10; i++) {
            MediumStatus[] statusWerte = MediumStatus.values();
            MediumStatus zufallsStatus = statusWerte[random.nextInt(statusWerte.length)];

            MediumTyp[] typWerte = MediumTyp.values();
            MediumTyp zufallsTyp = typWerte[random.nextInt(typWerte.length)];

            // Zufälliges Bild von picsum.photos
            String randomImageLink = "https://picsum.photos/200/300?random=" + faker.number().randomDigitNotZero();

            Random random = new Random();
            int seitenanzahl = 100 + random.nextInt(900); // 100 bis 999

            Buch buch = new Buch(faker.book().title(),faker.book().author(),"",randomImageLink,MediumStatus.VERFUEGBAR,MediumTyp.BUCH,faker.code().isbn13(),seitenanzahl);

            //  Zufälligen Standort erzeugen
            Standort standort = new Standort(
                    getZufallsStockwerk(),
                    faker.letterify("???").toUpperCase(),
                    "" + (random.nextInt(5) + 1)
            );
            standortRepository.save(standort);
            buch.setStandort(standort);

            //buchRepository.save(buch);
            mediumRepository.save(buch);
        }

        System.out.println(" 10 Fake-Bücher mit zufälligen Bildern wurden erfolgreich gespeichert.");
    }
    private Stockwerk getZufallsStockwerk() {
        Stockwerk[] werte = Stockwerk.values();
        return werte[random.nextInt(werte.length)];
    }

    public void generiereNutzer() {
        //  Dummy-Nutzer erzeugen, falls noch nicht vorhanden
        String email = "admin@example.com";
        if (nutzerRepository.findByEmail(email).isEmpty()) {
            Nutzer nutzer = new Nutzer();
            nutzer.setName("Max Mustermann");
            nutzer.setEmail(email);
            nutzer.setPasswort(demoUserPassword);
            nutzer.setAdresse("Musterstraße 1");
            nutzer.setGeburtsdatum(LocalDate.of(1990, 1, 1));
            nutzer.setRegistrierungsdatum(LocalDate.now());
            nutzer.setTelefonnummer("0123456789");

            nutzerRepository.save(nutzer);
            System.out.println("Dummy-Nutzer wurde erstellt.");
        } else {
            System.out.println("Dummy-Nutzer existiert bereits.");
        }
    }
}
