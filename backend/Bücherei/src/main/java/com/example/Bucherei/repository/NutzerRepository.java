package com.example.Bucherei.repository;

import com.example.Bucherei.model.Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutzerRepository extends JpaRepository<Nutzer, Long> {
    Optional<Nutzer> findByEmail(String email); // für Login/Prüfung bei Registrierung
}
