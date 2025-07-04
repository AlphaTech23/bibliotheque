package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.repositories.*;

@Service
public class AdherentService {

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

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

    public AdherentService(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

    @Transactional(readOnly = true)
    public boolean isActif(Integer adherentId, LocalDate datePret) {
        return abonnementRepository.existsByAdherentIdAndDateFinGreaterThanEqual(adherentId, datePret);
    }

    public List<Adherent> findAll() {
        return adherentRepository.findAll();
    }

    public Adherent findById(Integer id) {
        return adherentRepository.findById(id).orElse(null);
    }
}
