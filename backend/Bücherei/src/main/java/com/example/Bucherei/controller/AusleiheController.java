package com.example.Bucherei.controller;

import com.example.Bucherei.model.Ausleihe;
import com.example.Bucherei.service.AusleiheService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/ausleihen")
@Tag(name = "Ausleihen", description = "Operations related to Ausleihen (Lendings)")
public class AusleiheController {

    @Autowired
    private AusleiheService ausleiheService;

    @PostMapping
    public Ausleihe ausleihen(@RequestBody Ausleihe ausleihe) {
        return ausleiheService.ausleihen(
                ausleihe.getNutzer().getNutzerId(),
                ausleihe.getMedium().getMediumId(),
                ausleihe.getRueckgabedatum() != null
                        ? (int) ChronoUnit.DAYS.between(LocalDate.now(), ausleihe.getRueckgabedatum())
                        : 14
        );
    }

    @GetMapping("/nutzer/{nutzerId}")
    public List<Ausleihe> findeFuerNutzer(@PathVariable Long nutzerId) {
        return ausleiheService.findeAlleFuerNutzer(nutzerId);
    }

    @PutMapping("/{ausleiheId}/verlaengern")
    public Ausleihe verlaengern(@PathVariable Long ausleiheId, @RequestBody Ausleihe ausleihe) {
        return ausleiheService.verlaengern(ausleiheId, ausleihe.getRueckgabedatum());
    }
}

