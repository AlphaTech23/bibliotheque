package com.example.bibliotheque.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bibliotheque.models.Adherent;

public interface AdherentRepository extends JpaRepository<Adherent, Integer> {
    Optional<Adherent> findByEmail(String email);
}
