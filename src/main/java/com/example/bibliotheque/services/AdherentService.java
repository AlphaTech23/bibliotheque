package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.models.Exemplaire;
import com.example.bibliotheque.repositories.AbonnementRepository;
import com.example.bibliotheque.repositories.AdherentRepository;

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

    public Adherent getAdherent(Integer id) throws Exception {
        return adherentRepository.findById(id).orElseThrow(() -> new Exception("Adherent introuvable."));
    }

    public boolean isActif(Integer adherentId, LocalDate date) {
        return abonnementRepository.existsByAdherentIdAndDateFinGreaterThanEqual(adherentId, date);
    }

    public List<Adherent> findAll() {
        return adherentRepository.findAll();
    }

    public Adherent findById(Integer id) {
        return adherentRepository.findById(id).orElse(null);
    }

    public void verifierRestrictionAge(Adherent adherent, Exemplaire exemplaire, LocalDate dateReservation) throws Exception {
        int age = adherent.getAge(dateReservation);
        int restriction = exemplaire.getLivre().getRestriction();
        if (age < restriction) {
            throw new Exception("La restriction d'age pour ce livre est " + restriction);
        }
    }
}
