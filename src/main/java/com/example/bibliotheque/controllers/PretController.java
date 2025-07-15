package com.example.bibliotheque.controllers;

import java.time.LocalDate;
import java.util.List;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.services.AdherentService;
import com.example.bibliotheque.services.ExemplaireService;
import com.example.bibliotheque.services.TypePretService;
import com.example.bibliotheque.services.PretService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prets")
public class PretController {

    @Autowired
    private PretService pretService;

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private TypePretService typePretService;

    @Autowired
    private ExemplaireService exemplaireService;

    @PostMapping("/effectuer")
    public String effectuerPret(@RequestParam Integer adherentId,
                                @RequestParam Integer exemplaireId,
                                @RequestParam Integer typePretId,
                                @RequestParam LocalDate datePret,
                                Model model) {

        try {
            model.addAttribute("success", pretService.effectuerPret(adherentId, exemplaireId, typePretId, datePret));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "pret";
    }
    // Page d’accueil après connexion
    @GetMapping
    public String formPret(HttpSession session, Model model) {
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (bibliothecaire == null) {
            return "redirect:/bibliothecaire/login";
        }
        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "pret";
    }

    @GetMapping("/historique")
    public String rechercher(
            @RequestParam(required = false) Integer adherentId,
            @RequestParam(required = false) Integer typePretId,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            @RequestParam(required = false) String nom,
            Model model, HttpSession session) {

        LocalDate d1 = (dateDebut == null || dateDebut.isBlank()) ? null : LocalDate.parse(dateDebut);
        LocalDate d2 = (dateFin == null || dateFin.isBlank()) ? null : LocalDate.parse(dateFin);

        Adherent adherent = (Adherent) session.getAttribute("adherent");
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (adherent != null) {
            adherentId = adherent.getId();
        } else if (bibliothecaire == null) {
            return "redirect:/bibliothecaire/login";
        }

        List<Pret> prets = pretService.recherche(adherentId, typePretId, d1, d2, nom);

        model.addAttribute("adherents", adherentService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
        model.addAttribute("prets", prets);
        model.addAttribute("nom", nom);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("adherentId", adherentId);
        model.addAttribute("typePretId", typePretId);

        return "historique";
    }

    @GetMapping("/retour")
    public String formRetour(HttpSession session, Model model) {
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (bibliothecaire == null) {
            return "redirect:/login";
        }
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "pret-retour";
    }

    @PostMapping("/retour")
    public String rendreExemplaire(
            @RequestParam("exemplaireId") Integer exemplaireId,
            @RequestParam("dateRetour") LocalDate dateRetour,
            Model model) {

        try {
            model.addAttribute("success", pretService.rendreExemplaire(exemplaireId, dateRetour));
        } catch(IllegalArgumentException iae) {
            model.addAttribute("warning", iae.getMessage());
        }
        catch(Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "pret-retour";
    }

}
