package com.example.bibliotheque.controllers;

import com.example.bibliotheque.services.ExemplaireService;
import com.example.bibliotheque.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.models.Bibliothecaire;
import com.example.bibliotheque.models.Reservation;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping
    public String afficherFormulaire(HttpSession session, Model model) {
        Adherent adherent = (Adherent) session.getAttribute("adherent");
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (adherent == null && bibliothecaire == null) {
            return "redirect:/login";
        }
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "reserver";
    }

    @GetMapping("/historique")
    public String showReservations(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String livre,
            Model model, HttpSession session) {

        LocalDate d1 = (dateDebut != null && !dateDebut.isBlank()) ? LocalDate.parse(dateDebut) : null;
        LocalDate d2 = (dateFin != null && !dateFin.isBlank()) ? LocalDate.parse(dateFin) : null;
        Adherent adherent = (Adherent) session.getAttribute("adherent");
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (adherent != null) {
            nom = adherent.getEmail();
        } else if (bibliothecaire == null) {
            return "redirect:/bibliothecaire/login";
        }
        List<Reservation> results = reservationService.search(nom, livre, d1, d2, statut);

        model.addAttribute("reservations", results);
        model.addAttribute("nom", nom);
        model.addAttribute("livre", livre);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("statut", statut);

        return "reservation";
    }

    @PostMapping("/valider")
    public String valider(@RequestParam Integer id, Model model) {
        try {
            reservationService.validerReservation(id, true);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/reservation/historique";
    }

    @PostMapping("/refuser")
    public String refuser(@RequestParam Integer id, Model model) {
        try {
            reservationService.validerReservation(id, false);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/reservation/historique";
    }

    @PostMapping("/effectuer")
    public String effectuerReservation(
            @RequestParam("adherentId") Integer adherentId,
            @RequestParam("exemplaireId") Integer exemplaireId,
            @RequestParam("dateReservation") LocalDate dateReservation,
            @RequestParam("valide") Boolean valide,
            Model model) {
        try {
            String message = reservationService.effectuerReservation(adherentId, exemplaireId, dateReservation, valide);
            model.addAttribute("success", message);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("exemplaires", exemplaireService.findAll());
        return "reserver";
    }
}
