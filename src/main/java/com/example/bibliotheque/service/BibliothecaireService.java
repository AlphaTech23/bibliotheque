package com.example.bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.model.Bibliothecaire;
import com.example.bibliotheque.repository.BibliothecaireRepository;

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