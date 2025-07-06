package com.example.bibliotheque.controllers;

import com.example.bibliotheque.models.Livre;
import com.example.bibliotheque.services.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @GetMapping
    public String doSearch(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Integer minRestriction,
            @RequestParam(required = false) Integer maxRestriction,
            Model model) {

        List<Livre> livres = livreService.searchLivres(nom, minRestriction, maxRestriction);
        Map<Integer, Map<String, Object>> statsMap = new java.util.HashMap<>();

        for (Livre livre : livres) {
            statsMap.put(livre.getId(), livreService.getStatsForLivre(livre));
        }

        model.addAttribute("livres", livres);
        model.addAttribute("statsMap", statsMap);
        model.addAttribute("nom", nom);
        model.addAttribute("minRestriction", minRestriction);
        model.addAttribute("maxRestriction", maxRestriction);

        return "recherche";
    }
}
