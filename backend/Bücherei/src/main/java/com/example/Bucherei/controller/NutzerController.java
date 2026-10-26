package com.example.Bucherei.controller;

import com.example.Bucherei.model.Nutzer;
import com.example.Bucherei.service.NutzerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutzer")
@Tag(name = "Nutzer", description = "Operations related to Nutzer")
public class NutzerController {

    private final NutzerService nutzerService;

    @Autowired
    public NutzerController(NutzerService nutzerService) {
        this.nutzerService = nutzerService;
    }

    @PostMapping("/registrieren")
    public Nutzer registrieren(@Valid @RequestBody Nutzer nutzer) {
        return nutzerService.registrieren(nutzer);
    }

    @GetMapping("/{id}")
    public Nutzer findeNutzer(@PathVariable Long id) {
        return nutzerService.findeNutzerNachId(id);
    }
}
