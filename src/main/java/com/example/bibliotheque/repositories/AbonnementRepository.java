package com.example.bibliotheque.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bibliotheque.models.Abonnement;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    boolean existsByAdherentIdAndDateFinGreaterThanEqual(Integer adherentId, LocalDate date);
}
