package com.example.bibliotheque.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.bibliotheque.models.Abonnement;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    boolean existsByAdherentIdAndDateFinGreaterThanEqual(Integer adherentId, LocalDate date);
    @Query("SELECT a FROM Abonnement a WHERE a.adherent.id = :adherentId AND :date BETWEEN a.dateDebut AND a.dateFin")
    Optional<Abonnement> findActifByAdherentId(Integer adherentId, LocalDate date);

    Optional<Abonnement> findTopByAdherentIdOrderByDateFinDesc(Integer adherentId);
}
