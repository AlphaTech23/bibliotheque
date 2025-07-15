package com.example.bibliotheque.repositories;

import java.time.LocalDate;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bibliotheque.models.Penalite;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    @Query("SELECT MAX(p.dateDebut) FROM Penalite p WHERE p.adherent.id = :adId")
    Optional<LocalDate> findMaxDateDebutByAdherentId(@Param("adId") Integer adherentId);
    List<Penalite> findByAdherentId(Integer adherentId);
    Optional<Penalite> findTopByAdherentIdOrderByDateDebutDesc(Integer adherentId);
}
