package com.example.bibliotheque.repositories;

import com.example.bibliotheque.models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {

    List<Exemplaire> findByLivreId(Integer livreId);
}
