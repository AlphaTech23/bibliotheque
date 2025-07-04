package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.repositories.*;

@Service
public class ReservationService {

    @Autowired
    private AdherentService adherentService;
    @Autowired
    private AdherentRepository adherentRepo;
    @Autowired
    private ExemplaireRepository exRepo;
    @Autowired
    private StatutExemplaireRepository statutRepo;
    @Autowired
    private PretRepository pretRepo;
    @Autowired
    private ReservationRepository resRepo;
    @Autowired
    private PenaliteRepository penRepo;

    @Transactional
    public String effectuerReservation(Integer adherentId, Integer exemplaireId, LocalDate dateReservation, boolean valid) throws Exception {

        Optional<Adherent> oa = adherentRepo.findById(adherentId);
        Optional<Exemplaire> oe = exRepo.findById(exemplaireId);

        if (oa.isEmpty()) {
            throw new Exception("Adherent introuvable.");
        }

        if (oe.isEmpty()) {
            throw new Exception("Exemplaire introuvable.");
        }

        Adherent adherent = oa.get();
        Exemplaire exemplaire = oe.get();

        if (adherent.getAge(dateReservation) < exemplaire.getLivre().getRestriction()) {
            throw new Exception("La restriction d'age pour ce livre est " + exemplaire.getLivre().getRestriction());
        }

        if (!adherentService.isActif(adherentId, dateReservation)) {
            throw new Exception("Abonnement expiré.");
        }

        long reservationCount = resRepo.countByAdherentId(adherentId);
        if (reservationCount >= adherent.getTypeAdherent().getQuotaReservation()) {
            throw new Exception("Quota de réservations atteint.");
        }

        Optional<LocalDate> maxPenalite = penRepo.findMaxDateDebutByAdherentId(adherentId);
        if (maxPenalite.isPresent()) {
            LocalDate finPenalite = maxPenalite.get().plusDays(adherent.getTypeAdherent().getDureePenalite());
            if (!finPenalite.isBefore(dateReservation)) {
                throw new Exception("Période de pénalité en cours.");
            }
        }

        if (pretRepo.existsPretActifSurExemplaire(exemplaireId)) {
            throw new Exception("Exemplaire déjà prêté.");
        }

        Optional<StatutExemplaire> statut = statutRepo.findTopByExemplaireIdOrderByDateChangementDesc(exemplaireId);
        if (statut.isPresent()) {
            String etat = statut.get().getEtatExemplaire().getLibelle().toLowerCase();
            if (etat.contains("perdu") || etat.contains("détérioré")) {
                throw new Exception("Exemplaire inutilisable.");
            }
        }

        if (resRepo.existsByExemplaireIdAndDateReservation(exemplaireId, dateReservation)) {
            throw new Exception("Exemplaire réservé.");
        }

        Reservation reservation = new Reservation();
        reservation.setAdherent(adherent);
        reservation.setExemplaire(exemplaire);
        reservation.setDateReservation(dateReservation);
        if(valid) {
            reservation.setValide(dateReservation);
        }

        resRepo.save(reservation);

        return "Prêt effectué avec succès.";
    }
}
