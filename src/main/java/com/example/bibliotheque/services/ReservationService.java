package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.models.Exemplaire;
import com.example.bibliotheque.models.Reservation;
import com.example.bibliotheque.repositories.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private PenaliteService penaliteService;

    public List<Reservation> findAll() {
        return reservationRepository.findAllByOrderByDateReservationDesc();
    }

    public void validerReservation(Integer id, boolean valid) throws Exception {
        Optional<Reservation> opt = reservationRepository.findById(id);
        if (opt.isEmpty())
            throw new Exception("Réservation introuvable");

        Reservation r = opt.get();
        r.setValide(valid);
        reservationRepository.save(r);
    }

    public List<Reservation> search(String nom, String livre, LocalDate dateDebut, LocalDate dateFin, String statutStr) {
        Boolean valide = null;

        if ("valide".equalsIgnoreCase(statutStr)) {
            valide = true;
        } else if ("refusee".equalsIgnoreCase(statutStr)) {
            valide = false;
        } else if ("encours".equalsIgnoreCase(statutStr)) {
            valide = null;
        }

        return reservationRepository.search(
                (nom == null || nom.isBlank()) ? null : nom,
                livre,
                dateDebut,
                dateFin,
                statutStr == null ? null : valide);
    }

    @Transactional
    public String effectuerReservation(Integer adherentId, Integer exemplaireId, LocalDate dateReservation,
            boolean valid) throws Exception {

        Adherent adherent = adherentService.getAdherent(adherentId);
        Exemplaire exemplaire = exemplaireService.getExemplaire(exemplaireId);

        // Vérifications métier
        adherentService.verifierRestrictionAge(adherent, exemplaire, dateReservation);
        if (!adherentService.isActif(adherentId, dateReservation)) {
            throw new Exception("Abonnement expiré.");
        }

        long enCours = reservationRepository.countByAdherentIdAndValide(adherentId, true);
        long nonValide = reservationRepository.countByAdherentIdAndValideIsNull(adherentId);
        if ((enCours + nonValide) >= adherent.getTypeAdherent().getQuotaReservation()) {
            throw new Exception("Quota de réservations atteint.");
        }

        penaliteService.verifierPenaliteEnCours(adherent, dateReservation);

        exemplaireService.verifierPretActif(exemplaireId);
        exemplaireService.verifierStatutExemplaire(exemplaireId);

        if (exemplaireService.estReserveLeJour(exemplaireId, dateReservation)) {
            throw new Exception("Exemplaire réservé.");
        }

        Reservation reservation = new Reservation();
        reservation.setAdherent(adherent);
        reservation.setExemplaire(exemplaire);
        reservation.setDateReservation(dateReservation);
        reservation.setValide(valid);

        reservationRepository.save(reservation);

        return "Réservation effectuée avec succès.";
    }
}
