package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.repositories.*;

@Service
public class ProlongementService {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private ProlongementRepository prolongementRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private PenaliteService penaliteService;

    @Transactional
    public String prolongerPret(Integer pretId, LocalDate dateProlongement) throws Exception {
        Pret pret = pretRepository.findById(pretId).orElseThrow(() -> new Exception("Prêt introuvable."));

        if (pret.getDateRetour() != null) {
            throw new Exception("Ce prêt a déjà été retourné.");
        }

        if (prolongementRepository.existsByPret_Id(pretId)) {
            throw new Exception("Ce prêt a déjà été prolongé.");
        }

        Adherent adherent = pret.getAdherent();
        TypeAdherent typeAdherent = adherent.getTypeAdherent();

        LocalDate dateRetourInitial = pret.getDatePret().plusDays(typeAdherent.getDureePret());

        // Vérifier réservation existante sur la date de prolongement
        if (reservationRepository.existsByExemplaireIdAndDateReservation(pret.getExemplaire().getId(), dateProlongement)) {
            throw new Exception("Cet exemplaire est réservé aujourd’hui. Prolongement impossible.");
        }

        // La dateProlongement doit être faite au moins 'postProlongement' jours avant la date de retour initiale
        if (dateProlongement.isAfter(dateRetourInitial.minusDays(typeAdherent.getPostProlongement()))) {
            throw new Exception("Prolongement doit être demandé au moins " + typeAdherent.getPostProlongement() + " jours avant la date de retour.");
        }

        // Vérifier pénalité en cours
        penaliteService.verifierPenaliteEnCours(adherent, dateProlongement);

        Prolongement prolongement = new Prolongement();
        prolongement.setPret(pret);

        prolongementRepository.save(prolongement);

        return "Prolongement effectué jusqu’au " + dateRetourInitial.plusDays(typeAdherent.getDureeProlongement());
    }
}
