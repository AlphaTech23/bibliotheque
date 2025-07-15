package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.models.Penalite;
import com.example.bibliotheque.repositories.PenaliteRepository;

@Service
public class PenaliteService {

    @Autowired
    private PenaliteRepository penaliteRepository;

    public void verifierPenaliteEnCours(Adherent adherent, LocalDate date) throws Exception {
        Optional<LocalDate> maxDatePenalite = penaliteRepository.findMaxDateDebutByAdherentId(adherent.getId());
        if (maxDatePenalite.isPresent()) {
            LocalDate finPenalite = maxDatePenalite.get().plusDays(adherent.getTypeAdherent().getDureePenalite());
            if (!finPenalite.isBefore(date)) {
                throw new Exception("Période de pénalité en cours.");
            }
        }
    }

    public void ajouterPenalitePourRetard(Adherent adherent, LocalDate dateRetard, String motif) {
        Penalite penalite = new Penalite();
        penalite.setAdherent(adherent);

        Optional<Penalite> lastPenaliteOpt = penaliteRepository.findTopByAdherentIdOrderByDateDebutDesc(adherent.getId());
        LocalDate dateDebutPenalite;

        if (lastPenaliteOpt.isPresent()) {
            LocalDate finDernierePenalite = lastPenaliteOpt.get().getDateDebut().plusDays(adherent.getTypeAdherent().getDureePenalite());
            dateDebutPenalite = dateRetard.isAfter(finDernierePenalite) ? dateRetard : finDernierePenalite;
        } else {
            dateDebutPenalite = dateRetard;
        }

        penalite.setDateDebut(dateDebutPenalite);
        penalite.setMotif(motif);

        penaliteRepository.save(penalite);
    }

        public List<Penalite> getAll(Integer adherentId) {
            if(adherentId != null)
                return penaliteRepository.findAll();
            return penaliteRepository.findByAdherentId(adherentId);
        }
    
}
