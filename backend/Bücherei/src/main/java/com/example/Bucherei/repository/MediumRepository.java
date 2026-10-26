package com.example.Bucherei.repository;

import com.example.Bucherei.model.Medium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediumRepository extends JpaRepository<Medium, Long> {
    List<Medium> findByTitelContainingIgnoreCaseOrAutorContainingIgnoreCase(String titel, String autor);
}