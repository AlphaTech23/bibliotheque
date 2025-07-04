package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.models.Exemplaire;
import com.example.bibliotheque.models.Pret;
import com.example.bibliotheque.models.StatutExemplaire;
import com.example.bibliotheque.models.TypePret;
import com.example.bibliotheque.repositories.AbonnementRepository;
import com.example.bibliotheque.repositories.AdherentRepository;
import com.example.bibliotheque.repositories.ExemplaireRepository;
import com.example.bibliotheque.repositories.PenaliteRepository;
import com.example.bibliotheque.repositories.PretRepository;
import com.example.bibliotheque.repositories.ReservationRepository;
import com.example.bibliotheque.repositories.StatutExemplaireRepository;
import com.example.bibliotheque.repositories.TypePretRepository;

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

    @Transactional
    public String effectuerPret(Integer adherentId, Integer exemplaireId, Integer typePretId) throws Exception {

        LocalDate today = LocalDate.now();

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

        if(adherent.getAge() < exemplaire.getLivre().getRestriction()) {
            throw new Exception("La restriction d'age pour ce livre est " + exemplaire.getLivre().getRestriction());
        }

        if (!adherentService.isActif(adherentId, today)) {
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
                if (!finPenalite.isBefore(today)) {
                    throw new Exception("Période de pénalité en cours.");
                }
            }
        }

        if (pretRepo.existsPretActifSurExemplairePourDate(exemplaireId, today)) {
            throw new Exception("Exemplaire déjà prêté.");
        }

        Optional<StatutExemplaire> statut = statutRepo.findTopByExemplaireIdOrderByDateChangementDesc(exemplaireId);
        if (statut.isPresent()) {
            String etat = statut.get().getEtatExemplaire().getLibelle().toLowerCase();
            if (etat.contains("perdu") || etat.contains("détérioré")) {
                throw new Exception("Exemplaire inutilisable.");
            }
        }

        if (resRepo.existsByExemplaireIdAndDateReservation(exemplaireId, today)) {
            throw new Exception("Exemplaire réservé.");
        }

        Pret pret = new Pret();
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaire);
        pret.setDatePret(today);
        pret.setTypePret(typePret);

        if (typePret.getLibelle().equalsIgnoreCase("sur place")) {
            pret.setDateRetour(today);
        }

        pretRepo.save(pret);

        return "Prêt effectué avec succès.";
    }
}
