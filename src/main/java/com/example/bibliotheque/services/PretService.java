package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.repositories.*;

@Service
public class PretService {

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private StatutExemplaireRepository statutExemplaireRepository;

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private TypePretRepository typePretRepository;

    @Autowired
    private ProlongementRepository prolongementRepository;

    @Autowired
    private PenaliteService penaliteService;

    @Transactional
    public String effectuerPret(Integer adherentId, Integer exemplaireId, Integer typePretId, LocalDate datePret) throws Exception {
        Adherent adherent = adherentRepository.findById(adherentId).orElseThrow(() -> new Exception("Adherent introuvable."));
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElseThrow(() -> new Exception("Exemplaire introuvable."));
        TypePret typePret = typePretRepository.findById(typePretId).orElseThrow(() -> new Exception("Type prêt introuvable."));

        // Restrictions d'age
        adherentService.verifierRestrictionAge(adherent, exemplaire, datePret);

        if (!adherentService.isActif(adherentId, datePret)) {
            throw new Exception("Abonnement expiré.");
        }

        // Quota prêt
        if (!"sur place".equalsIgnoreCase(typePret.getLibelle())) {
            long countPret = pretRepository.countByAdherentIdAndDateRetourIsNullAndTypePret_LibelleNot(adherentId, "sur place");
            if (countPret >= adherent.getTypeAdherent().getQuotaExemplaire()) {
                throw new Exception("Quota de prêt dépassé.");
            }
            penaliteService.verifierPenaliteEnCours(adherent, datePret);
        }

        exemplaireService.verifierPretActif(exemplaireId);
        exemplaireService.verifierStatutExemplaire(exemplaireId);

        if (reservationRepository.existsByExemplaireIdAndDateReservation(exemplaireId, datePret)) {
            throw new Exception("Exemplaire réservé.");
        }

        Pret pret = new Pret();
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaire);
        pret.setDatePret(datePret);
        pret.setTypePret(typePret);

        if ("sur place".equalsIgnoreCase(typePret.getLibelle())) {
            pret.setDateRetour(datePret);
        }

        pretRepository.save(pret);

        return "Prêt effectué avec succès.";
    }

    @Transactional
    public String rendreExemplaire(Integer exemplaireId, LocalDate dateRetour) throws Exception {
        Pret pret = pretRepository.findByExemplaireIdAndDateRetourIsNull(exemplaireId)
                .orElseThrow(() -> new Exception("Aucun prêt actif pour cet exemplaire."));

        Adherent adherent = pret.getAdherent();
        TypeAdherent typeAdherent = adherent.getTypeAdherent();

        LocalDate dateLimite = pret.getDatePret().plusDays(typeAdherent.getDureePret());
        Optional<Prolongement> prolongementOpt = prolongementRepository.findByPret_Id(pret.getId());
        if (prolongementOpt.isPresent()) {
            dateLimite = dateLimite.plusDays(typeAdherent.getDureeProlongement());
        }

        if (dateRetour.isAfter(dateLimite)) {
            penaliteService.ajouterPenalitePourRetard(adherent, dateRetour, "Retour en retard le " + dateRetour);
            pret.setDateRetour(dateRetour);
            pretRepository.save(pret);
            throw new IllegalArgumentException("Pénalité ajoutée pour retard.");
        }

        pret.setDateRetour(dateRetour);
        pretRepository.save(pret);

        return "Retour enregistré avec succès.";
    }
}
