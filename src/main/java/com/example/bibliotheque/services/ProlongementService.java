package com.example.bibliotheque.services;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

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

    @Transactional
    public String prolongerPret(Integer pretId, LocalDate dateProlongement) throws Exception {
        Optional<Pret> optPret = pretRepository.findById(pretId);

        if (optPret.isEmpty()) {
            throw new Exception("Prêt introuvable.");
        }

        Pret pret = optPret.get();

        if (pret.getDateRetour() != null) {
            throw new Exception("Ce prêt a déjà été retourné.");
        }

        // Vérifier s'il est déjà prolongé
        if (prolongementRepository.existsByPret_Id(pretId)) {
            throw new Exception("Ce prêt a déjà été prolongé.");
        }

        Adherent adherent = pret.getAdherent();
        int quotaProlongement = adherent.getTypeAdherent().getDureeProlongement();

        // Vérifier réservation existante
        boolean reservationPourExemplaire = reservationRepository.existsByExemplaireIdAndDateReservation(pret.getExemplaire().getId(), dateProlongement);
        if (reservationPourExemplaire) {
            throw new Exception("Cet exemplaire est réservé aujourd’hui. Prolongement impossible.");
        }

        // Prolonger le prêt : ajouter dans prolongement
        Prolongement prolongement = new Prolongement();

        // Étendre la date retour prévue
        LocalDate nouvelleDateRetour = pret.getDatePret().plusDays(
            adherent.getTypeAdherent().getDureePret() + quotaProlongement
        );

        LocalDate dateRetour = pret.getDatePret().plusDays(
            adherent.getTypeAdherent().getDureePret()
        );

        if(dateProlongement.isAfter(dateRetour)) {
            Penalite penalite = new Penalite();
            penalite.setAdherent(adherent);

            Optional<Penalite> lastPenalite = penaliteRepository.findTopByAdherentIdOrderByDateDebutDesc(adherent.getId());

            LocalDate dateDebutPenalite;
            if (lastPenalite.isPresent()) {
                LocalDate finPenalite = lastPenalite.get().getDateDebut().plusDays(adherent.getTypeAdherent().getDureePenalite());
                dateDebutPenalite = dateRetour.isAfter(finPenalite) ? dateRetour : finPenalite;
            } else {
                dateDebutPenalite = dateRetour;
            }

            penalite.setDateDebut(dateDebutPenalite);
            penalite.setMotif("Retour en retard le " + dateRetour);
            penaliteRepository.save(penalite);

            throw new Exception("Pénalité ajoutée pour retard.");
        }
        prolongement.setPret(pret);
        prolongementRepository.save(prolongement);

        return "Prolongement effectué jusqu’au " + nouvelleDateRetour;
    }
}
