package com.example.bibliotheque.controllers;

import com.example.bibliotheque.services.ProlongementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import com.example.bibliotheque.models.Bibliothecaire;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prolongement")
public class ProlongementController {

    @Autowired
    private ProlongementService prolongementService;

    @GetMapping
    public String afficherFormulaire(HttpSession session) {
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (bibliothecaire == null) {
            return "redirect:/bibliothecaire/login";
        }
        return "prolongement";
    }

    @PostMapping("/effectuer")
    public String effectuerProlongement(
            @RequestParam("pretId") Integer pretId,
            @RequestParam("dateProlongement") LocalDate dateProlongement,
            Model model) {
        try {
            String resultat = prolongementService.prolongerPret(pretId, dateProlongement);
            model.addAttribute("success", resultat);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "prolongement";
    }
}
