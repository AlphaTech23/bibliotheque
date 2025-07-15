package com.example.bibliotheque.repositories;

import com.example.bibliotheque.models.JourFerier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface JourFerierRepository extends JpaRepository<JourFerier, Integer> {
    Optional<JourFerier> findByDateFerier(LocalDate date);
}