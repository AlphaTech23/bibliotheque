package com.example.bibliotheque.repositories;

import com.example.bibliotheque.models.JourOuvrable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JourOuvrableRepository extends JpaRepository<JourOuvrable, Integer> {
    Optional<JourOuvrable> findByJourSemaine(Integer jourSemaine);
}