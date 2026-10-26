package com.example.Bucherei.controller;

import com.example.Bucherei.model.Medium;
import com.example.Bucherei.service.MediumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medien")
@Tag(name = "Medien", description = "Operations related to Medien")
public class MediumController {

    private final MediumService mediumService;

    @Autowired
    public MediumController(MediumService mediumService) {
        this.mediumService = mediumService;
    }

    @PostMapping
    public Medium erstellen(@RequestBody Medium medium) {
        return mediumService.speichern(medium);
    }

    @GetMapping
    public List<Medium> findeAlle() {
        return mediumService.findeAlle();
    }

    @GetMapping("/{id}")
    public Medium findeNachId(@PathVariable Long id) {
        return mediumService.findeNachId(id);
    }

    @GetMapping("/suche")
    public List<Medium> suche(@RequestParam String begriff) {
        return mediumService.suche(begriff);
    }
}
