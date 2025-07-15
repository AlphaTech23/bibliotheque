package com.example.bibliotheque.controllers;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/abonnements")
public class AbonnementController {

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private TypeAdherentService typeAdherentService;
    
    @Autowired
    private AdherentService adherentService;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("typesAdherent", typeAdherentService.findAll());
        return "abonnement"; 
    }

    @PostMapping
    public String creerAbonnement(
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String motDePasse,
            @RequestParam Integer typeId,
            @RequestParam String dateNaissance,
            @RequestParam String dateDebut,
            @RequestParam String dateFin,
            Model model) {
        try {
            Adherent adherent = new Adherent();
            adherent.setNom(nom);
            adherent.setEmail(email);
            adherent.setMotDePasse(motDePasse);
            TypeAdherent typeAdherent = new TypeAdherent();
            typeAdherent.setId(typeId);
            adherent.setTypeAdherent(typeAdherent);
            adherent.setDateNaissance(LocalDate.parse(dateNaissance));

            LocalDate debut = LocalDate.parse(dateDebut);
            LocalDate fin = LocalDate.parse(dateFin);

            String message = abonnementService.creerAbonnementComplet(adherent, debut, fin);
            model.addAttribute("message", message);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "abonnement";
    }

    // Page pour le réabonnement
    @GetMapping("/renew")
    public String showRenewForm(Model model) {
        model.addAttribute("adherents", adherentService.findAll());
        return "reabonnement";
    }

    @PostMapping("/renew")
    public String renewAbonnement(
            @RequestParam Integer adherentId,
            @RequestParam String dateDebut,
            @RequestParam String dateFin,
            Model model) {
        try {
            LocalDate d1 = LocalDate.parse(dateDebut);
            LocalDate d2 = LocalDate.parse(dateFin);
            String message = abonnementService.renouvelerAvecDates(adherentId, d1, d2);
            model.addAttribute("message", message);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return showRenewForm(model);
    }

}
