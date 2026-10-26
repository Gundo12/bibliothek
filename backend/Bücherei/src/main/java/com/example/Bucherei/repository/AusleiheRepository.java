package com.example.Bucherei.repository;

import com.example.Bucherei.model.Ausleihe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


public interface AusleiheRepository extends JpaRepository<Ausleihe, Long> {
    List<Ausleihe> findByNutzerNutzerId(Long nutzerId);
}
