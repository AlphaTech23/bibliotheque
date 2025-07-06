package com.example.bibliotheque.services;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepo;

    @Autowired
    private ExemplaireRepository exemplaireRepo;

    @Autowired
    private PretRepository pretRepo;

    @Autowired
    private ProlongementRepository prolongementRepo;

    /**
     * Recherche multicritère sur les livres
     */
    public List<Livre> searchLivres(String nom, Integer minRestriction, Integer maxRestriction) {

        boolean hasNom = nom != null && !nom.isBlank();
        boolean hasMin = minRestriction != null;
        boolean hasMax = maxRestriction != null;

        // Tous null ou vide -> tous les livres
        if (!hasNom && !hasMin && !hasMax) {
            return livreRepo.findAll();
        }

        // nom seul
        if (hasNom && !hasMin && !hasMax) {
            return livreRepo.findByNomContainingIgnoreCase(nom);
        }

        // restriction seule min max
        if (!hasNom && hasMin && hasMax) {
            return livreRepo.findByRestrictionBetween(minRestriction, maxRestriction);
        }

        // restriction seule min
        if (!hasNom && hasMin && !hasMax) {
            return livreRepo.findByRestrictionGreaterThanEqual(minRestriction);
        }

        // restriction seule max
        if (!hasNom && !hasMin && hasMax) {
            return livreRepo.findByRestrictionLessThanEqual(maxRestriction);
        }

        // nom + restriction entre min max
        if (hasNom && hasMin && hasMax) {
            return livreRepo.findByNomContainingIgnoreCaseAndRestrictionBetween(nom, minRestriction, maxRestriction);
        }

        // nom + restriction min seulement
        if (hasNom && hasMin && !hasMax) {
            // On peut filtrer par restriction >= min puis filtrer côté Java (car pas méthode spécifique)
            return livreRepo.findByNomContainingIgnoreCase(nom).stream()
                    .filter(l -> l.getRestriction() >= minRestriction)
                    .collect(Collectors.toList());
        }

        // nom + restriction max seulement
        if (hasNom && !hasMin && hasMax) {
            // Pareil on filtre côté Java
            return livreRepo.findByNomContainingIgnoreCase(nom).stream()
                    .filter(l -> l.getRestriction() <= maxRestriction)
                    .collect(Collectors.toList());
        }

        // Aucun autre cas attendu, mais pour sécurité :
        return livreRepo.findAll();
    }

    /**
     * Calcule les stats par livre + détails des prêts actifs
     */
    public Map<String, Object> getStatsForLivre(Livre livre) {
        List<Exemplaire> exemplaires = exemplaireRepo.findByLivreId(livre.getId());

        int total = exemplaires.size();
        int dispo = 0;
        int indispo = 0;

        List<Map<String, Object>> pretInfos = new ArrayList<>();

        for (Exemplaire e : exemplaires) {
            Optional<Pret> pretOpt = pretRepo.findByExemplaireIdAndDateRetourIsNull(e.getId());

            boolean estDisponible = true;

            if (pretOpt.isPresent()) {
                estDisponible = false;
                Pret p = pretOpt.get();

                LocalDate dateDispo = p.getDatePret()
                        .plusDays(p.getAdherent().getTypeAdherent().getDureePret());

                if (prolongementRepo.existsByPret_Id(p.getId())) {
                    dateDispo = dateDispo.plusDays(p.getAdherent().getTypeAdherent().getDureeProlongement());
                }

                Map<String, Object> info = new HashMap<>();
                info.put("adherent", p.getAdherent());
                info.put("dateDispo", dateDispo);
                pretInfos.add(info);
            }

            if (estDisponible) dispo++;
            else indispo++;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("disponible", dispo);
        result.put("indisponible", indispo);
        result.put("pretInfos", pretInfos);

        return result;
    }
}
