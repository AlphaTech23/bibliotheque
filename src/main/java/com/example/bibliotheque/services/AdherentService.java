package com.example.bibliotheque.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.repositories.AdherentRepository;

@Service
public class AdherentService {

    @Autowired
    private AdherentRepository adherentRepository;

    public Adherent inscrire(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    public Optional<Adherent> login(String email, String motDePasse) {
        Optional<Adherent> adherentOpt = adherentRepository.findByEmail(email);
        if (adherentOpt.isPresent() && adherentOpt.get().getMotDePasse().equals(motDePasse)) {
            return adherentOpt;
        }
        return Optional.empty();
    }
}
