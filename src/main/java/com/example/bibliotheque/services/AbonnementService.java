package com.example.bibliotheque.services;

import com.example.bibliotheque.models.Abonnement;
import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.repositories.AbonnementRepository;
import com.example.bibliotheque.repositories.AdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepo;

    @Autowired
    private AdherentRepository adherentRepo;

    public String creerAbonnementComplet(Adherent adherent, LocalDate dateDebut, LocalDate dateFin) throws Exception {
        if (dateFin.isBefore(dateDebut)) {
            throw new Exception("La date de fin doit être après la date de début.");
        }

        // Sauvegarde ou mise à jour de l'adhérent
        Adherent savedAdherent = adherentRepo.save(adherent);

        // Vérifier qu'il n'a pas déjà un abonnement actif dans la période
        Optional<Abonnement> actif = abonnementRepo.findActifByAdherentId(savedAdherent.getId(), dateDebut);
        if (actif.isPresent()) {
            throw new Exception("Cet adhérent a déjà un abonnement actif à cette date.");
        }

        // Création de l'abonnement
        Abonnement abonnement = new Abonnement();
        abonnement.setAdherent(savedAdherent);
        abonnement.setDateDebut(dateDebut);
        abonnement.setDateFin(dateFin);

        abonnementRepo.save(abonnement);
        return "Abonnement créé pour " + savedAdherent.getNom() + " jusqu'au " + dateFin;
    }

    public String renouvelerAvecDates(Integer adherentId, LocalDate nouvelleDateDebut, LocalDate nouvelleDateFin)
            throws Exception {
        Optional<Adherent> opt = adherentRepo.findById(adherentId);
        if (opt.isEmpty())
            throw new Exception("Adhérent introuvable");

        if (nouvelleDateDebut.isAfter(nouvelleDateFin)) {
            throw new Exception("Date de début après la date de fin.");
        }

        // Vérifie si un abonnement existe dans la période spécifiée
        Optional<Abonnement> enConflit = abonnementRepo.findActifByAdherentId(adherentId, nouvelleDateDebut);
        if (enConflit.isPresent()) {
            throw new Exception("L'adhérent a déjà un abonnement actif pendant cette période.");
        }

        Abonnement nouveau = new Abonnement();
        nouveau.setAdherent(opt.get());
        nouveau.setDateDebut(nouvelleDateDebut);
        nouveau.setDateFin(nouvelleDateFin);
        abonnementRepo.save(nouveau);

        return "Réabonnement effectué jusqu'au " + nouvelleDateFin;
    }

    public boolean estActif(Integer adherentId) {
        return abonnementRepo.findActifByAdherentId(adherentId, LocalDate.now()).isPresent();
    }

    public Optional<Abonnement> getDernierAbonnement(Integer adherentId) {
        return abonnementRepo.findTopByAdherentIdOrderByDateFinDesc(adherentId);
    }
}
