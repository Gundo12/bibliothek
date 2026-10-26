package com.example.Bucherei.repository;

import com.example.Bucherei.model.Reservierung;
import com.example.Bucherei.model.ReservierungsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservierungRepository extends JpaRepository<Reservierung, Long> {
    List<Reservierung> findByNutzerNutzerId(Long nutzerId);

    List<Reservierung> findByMediumMediumIdAndStatus(Long mediumId, ReservierungsStatus reservierungsStatus);

    Optional<Reservierung> findByMediumMediumId(Long mediumId);

    List<Reservierung> findByReserviertBisBefore(LocalDateTime zeitpunkt);

}
