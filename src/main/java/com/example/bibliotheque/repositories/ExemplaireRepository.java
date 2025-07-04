package com.example.bibliotheque.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bibliotheque.models.Exemplaire;


@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {}
