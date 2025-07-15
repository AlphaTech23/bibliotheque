package com.example.bibliotheque.controllers;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PenaliteController {

    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("/penalites")
    public String getAllPenalites(Model model, HttpSession session) {
        Integer adherentId = null;
        Adherent adherent = (Adherent) session.getAttribute("adherent");
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (adherent != null) {
            adherentId = adherent.getId();
        } else if (bibliothecaire == null) {
            return "redirect:/bibliothecaire/login";
        }
        List<Penalite> penalites= penaliteService.getAll(adherentId);
        
        model.addAttribute("penalites", penalites);
        
        return "penalites";
    }
}