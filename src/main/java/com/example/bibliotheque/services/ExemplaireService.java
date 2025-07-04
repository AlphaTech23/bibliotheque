package com.example.bibliotheque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.models.Exemplaire;
import com.example.bibliotheque.models.StatutExemplaire;
import com.example.bibliotheque.repositories.ExemplaireRepository;
import com.example.bibliotheque.repositories.PretRepository;
import com.example.bibliotheque.repositories.StatutExemplaireRepository;
import com.example.bibliotheque.repositories.ReservationRepository;

@Service
public class ExemplaireService {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StatutExemplaireRepository statutExemplaireRepository;

    public Exemplaire getExemplaire(Integer id) throws Exception {
        return exemplaireRepository.findById(id).orElseThrow(() -> new Exception("Exemplaire introuvable."));
    }

    public void verifierStatutExemplaire(Integer exemplaireId) throws Exception {
        Optional<StatutExemplaire> statut = statutExemplaireRepository.findTopByExemplaireIdOrderByDateChangementDesc(exemplaireId);
        if (statut.isPresent()) {
            String etat = statut.get().getEtatExemplaire().getLibelle().toLowerCase();
            if (etat.contains("perdu") || etat.contains("détérioré")) {
                throw new Exception("Exemplaire inutilisable.");
            }
        }
    }

    public void verifierPretActif(Integer exemplaireId) throws Exception {
        if (pretRepository.existsPretActifSurExemplaire(exemplaireId)) {
            throw new Exception("Exemplaire déjà prêté.");
        }
    }

    public boolean estReserveLeJour(Integer exemplaireId, java.time.LocalDate date) {
        return reservationRepository.existsByExemplaireIdAndDateReservation(exemplaireId, date);
    }
    
    public List<Exemplaire> findAll() {
        return exemplaireRepository.findAll();
    }

    public Exemplaire findById(Integer id) {
        return exemplaireRepository.findById(id).orElse(null);
    }
}