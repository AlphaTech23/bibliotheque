package com.example.bibliotheque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bibliotheque.models.StatutExemplaire;
import java.util.*;

public interface StatutExemplaireRepository extends JpaRepository<StatutExemplaire, Integer> {
    Optional<StatutExemplaire> findTopByExemplaireIdOrderByDateChangementDesc(Integer exemplaireId);
    List<StatutExemplaire> findByExemplaireIdOrderByDateChangementDesc(Integer exemplaireId);
}
    