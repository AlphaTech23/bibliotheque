package com.example.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.models.Bibliothecaire;
import com.example.bibliotheque.repositories.BibliothecaireRepository;

@Service
public class BibliothecaireService {
    @Autowired
    private BibliothecaireRepository bibliothecaireRepository;

    public Bibliothecaire authentifier(String nom, String motDePasse) {
        Bibliothecaire bibliothecaire = bibliothecaireRepository.findByNom(nom);
        if (bibliothecaire != null && bibliothecaire.getMot_de_passe().equals(motDePasse)) {
            return bibliothecaire;
        }
        return null;
    }
}