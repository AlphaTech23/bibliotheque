package com.example.bibliotheque.repositories;

import java.util.Optional;

import com.example.bibliotheque.models.Prolongement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    boolean existsByPret_Id(Integer pretId);
    long countByPret_Adherent_IdAndPret_DateRetourIsNull(Integer adherentId);
    Optional<Prolongement> findByPret_Id(Integer pretId);

}
