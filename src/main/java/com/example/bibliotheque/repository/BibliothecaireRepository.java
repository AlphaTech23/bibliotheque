package com.example.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bibliotheque.model.Bibliothecaire;

@Repository
public interface BibliothecaireRepository extends JpaRepository<Bibliothecaire, Integer> {
    Bibliothecaire findByNom(String nom);
}