package com.example.bibliotheque.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bibliotheque.models.Adherent;
import com.example.bibliotheque.services.AdherentService;
import com.example.bibliotheque.services.TypeAdherentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdherentController {

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private TypeAdherentService typeAdherentService;

    // Affiche le formulaire de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Traitement du login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String motDePasse,
                        Model model,
                        HttpSession session) {
        return adherentService.login(email, motDePasse)
                .map(adherent -> {
                    session.setAttribute("adherent", adherent); // Stocker en session
                    return "redirect:/dashboard";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Email ou mot de passe incorrect");
                    return "login";
                });
    }

    // Affiche le formulaire d'inscription
    @GetMapping("/inscription")
    public String showRegisterForm(Model model) {
        model.addAttribute("adherent", new Adherent());
        model.addAttribute("types", typeAdherentService.findAll()); // Pour la liste déroulante
        return "inscription";
    }

    // Traitement de l'inscription
    @PostMapping("/inscription")
    public String register(@ModelAttribute Adherent adherent,
                           HttpSession session) {
        Adherent saved = adherentService.inscrire(adherent);
        session.setAttribute("adherent", saved); // Connexion automatique
        return "redirect:/dashboard";
    }


    // Déconnexion
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Détruire toute la session
        return "redirect:/login";
    }
}
