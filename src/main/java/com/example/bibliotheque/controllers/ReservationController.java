package com.example.bibliotheque.controllers;

import com.example.bibliotheque.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.models.Bibliothecaire;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String afficherFormulaire(HttpSession session) {
        Adherent adherent = (Adherent) session.getAttribute("adherent");
        if (adherent == null) {
            Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
            if (bibliothecaire == null) {
                return "redirect:/login";
            }
        }
        return "reserver";
    }

    @PostMapping("/effectuer")
    public String effectuerReservation(
            @RequestParam("adherentId") Integer adherentId,
            @RequestParam("exemplaireId") Integer exemplaireId,
            @RequestParam("dateReservation") LocalDate dateReservation,
            @RequestParam("valide") Boolean valide,
            Model model
    ) {
        try {    
            String message = reservationService.effectuerReservation(adherentId, exemplaireId, dateReservation, valide);
            model.addAttribute("success", message);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "reserver";
    }
}
