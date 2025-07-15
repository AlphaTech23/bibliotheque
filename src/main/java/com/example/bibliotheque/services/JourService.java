package com.example.bibliotheque.services;

import com.example.bibliotheque.models.JourFerier;
import com.example.bibliotheque.models.JourOuvrable;
import com.example.bibliotheque.repositories.JourFerierRepository;
import com.example.bibliotheque.repositories.JourOuvrableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class JourService {

    @Autowired
    private JourFerierRepository jourFerierRepository;

    @Autowired
    private JourOuvrableRepository jourOuvrableRepository;

    public LocalDate ajusterDate(LocalDate date) {
        LocalDate dateCourante = date;
        int compteur = 0;
        int limiteRecursion = 10;

        List<JourOuvrable> jourOuvrables = jourOuvrableRepository.findAll();

        while (compteur < limiteRecursion) {
            compteur++;

            // 1. Vérifie si c’est un jour férié
            Optional<JourFerier> jourFerierOpt = jourFerierRepository.findByDateFerier(dateCourante);
            if (jourFerierOpt.isPresent()) {
                JourFerier jourFerier = jourFerierOpt.get();
                dateCourante = jourFerier.isAvant() ? dateCourante.minusDays(1) : dateCourante.plusDays(1);
                continue;
            }

            if (jourOuvrables.isEmpty()) {
                return dateCourante;
            }

            int jourSemaine = dateCourante.getDayOfWeek().getValue();
            Optional<JourOuvrable> jourOuvrableOpt = jourOuvrableRepository.findByJourSemaine(jourSemaine);
            if (jourOuvrableOpt.isPresent()) {
                break; 
            }

            boolean allerAvant = jourOuvrables.get(0).isAvant();
            dateCourante = allerAvant ? dateCourante.minusDays(1) : dateCourante.plusDays(1);
        }

        return dateCourante;
    }

}