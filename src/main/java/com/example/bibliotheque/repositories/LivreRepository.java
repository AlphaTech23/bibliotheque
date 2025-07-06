package com.example.bibliotheque.repositories;

import com.example.bibliotheque.models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Integer> {

    List<Livre> findByNomContainingIgnoreCase(String nom);

    List<Livre> findByRestrictionGreaterThanEqual(Integer minRestriction);

    List<Livre> findByRestrictionLessThanEqual(Integer maxRestriction);

    List<Livre> findByNomContainingIgnoreCaseAndRestrictionBetween(String nom, Integer minRestriction, Integer maxRestriction);

    List<Livre> findByRestrictionBetween(Integer minRestriction, Integer maxRestriction);

    List<Livre> findAll(); // déjà existante via JpaRepository
}

