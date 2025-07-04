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
public class PretService {
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
    @Autowired
    private TypePretRepository typePretRepo;
    @Autowired
    private ProlongementRepository prolRepo;

    @Transactional
    public String effectuerPret(Integer adherentId, Integer exemplaireId, Integer typePretId, LocalDate datePret) throws Exception {

        Optional<Adherent> oa = adherentRepo.findById(adherentId);
        Optional<Exemplaire> oe = exRepo.findById(exemplaireId);
        Optional<TypePret> otp = typePretRepo.findById(typePretId);

        if (oa.isEmpty()) {
            throw new Exception("Adherent introuvable.");
        }

        if(oe.isEmpty()) {
            throw new Exception("Exemplaire introuvable.");
        }

        if(otp.isEmpty()) {
            throw new Exception("Type pret introuvable");
        }

        Adherent adherent = oa.get();
        Exemplaire exemplaire = oe.get();
        TypePret typePret = otp.get();

        if(adherent.getAge(datePret) < exemplaire.getLivre().getRestriction()) {
            throw new Exception("La restriction d'age pour ce livre est " + exemplaire.getLivre().getRestriction());
        }

        if (!adherentService.isActif(adherentId, datePret)) {
            throw new Exception("Abonnement expiré.");
        }

        if (!typePret.getLibelle().equalsIgnoreCase("sur place")) {

            long pretCount = pretRepo.countByAdherentIdAndDateRetourIsNullAndTypePret_LibelleNot(adherentId, "sur place");
            if (pretCount >= adherent.getTypeAdherent().getQuotaExemplaire()) {
                throw new Exception("Quota de prêt dépassé.");

            }

            Optional<LocalDate> maxPenalite = penRepo.findMaxDateDebutByAdherentId(adherentId);
            if (maxPenalite.isPresent()) {
                LocalDate finPenalite = maxPenalite.get().plusDays(adherent.getTypeAdherent().getDureePenalite());
                if (!finPenalite.isBefore(datePret)) {
                    throw new Exception("Période de pénalité en cours.");
                }
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

        if (resRepo.existsByExemplaireIdAndDateReservation(exemplaireId, datePret)) {
            throw new Exception("Exemplaire réservé.");
        }

        Pret pret = new Pret();
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaire);
        pret.setDatePret(datePret);
        pret.setTypePret(typePret);

        if (typePret.getLibelle().equalsIgnoreCase("sur place")) {
            pret.setDateRetour(datePret);
        }

        pretRepo.save(pret);

        return "Prêt effectué avec succès.";
    }

    @Transactional
    public String rendreExemplaire(Integer exemplaireId, LocalDate dateRetour) throws Exception {
        Optional<Pret> optPret = pretRepo.findByExemplaireIdAndDateRetourIsNull(exemplaireId);
        if (optPret.isEmpty()) {
            throw new Exception("Aucun prêt actif pour cet exemplaire.");
        }

        Pret pret = optPret.get();
        Adherent adherent = pret.getAdherent();
        TypeAdherent typeAdherent = adherent.getTypeAdherent();

        LocalDate dateLimite = pret.getDatePret().plusDays(typeAdherent.getDureePret());
        Optional<Prolongement> prolongement = prolRepo.findByPret_Id(pret.getId());
        if (prolongement.isPresent()) {
            dateLimite = dateLimite.plusDays(typeAdherent.getDureeProlongement());
        }
        if (dateRetour.isAfter(dateLimite)) {
            Penalite penalite = new Penalite();
            penalite.setAdherent(adherent);

            Optional<Penalite> lastPenalite = penRepo.findTopByAdherentIdOrderByDateDebutDesc(adherent.getId());

            LocalDate dateDebutPenalite;
            if (lastPenalite.isPresent()) {
                LocalDate finPenalite = lastPenalite.get().getDateDebut().plusDays(typeAdherent.getDureePenalite());
                dateDebutPenalite = dateRetour.isAfter(finPenalite) ? dateRetour : finPenalite;
            } else {
                dateDebutPenalite = dateRetour;
            }

            penalite.setDateDebut(dateDebutPenalite);
            penalite.setMotif("Retour en retard le " + dateRetour);
            penRepo.save(penalite);
            pret.setDateRetour(dateRetour);
            pretRepo.save(pret);

            throw new IllegalArgumentException("Pénalité ajoutée pour retard.");
        }

        pret.setDateRetour(dateRetour);
        pretRepo.save(pret);

        return "Retour enregistré avec succès.";
    }

}
