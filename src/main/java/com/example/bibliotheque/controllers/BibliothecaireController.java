package com.example.bibliotheque.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bibliotheque.models.Bibliothecaire;
import com.example.bibliotheque.services.BibliothecaireService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bibliothecaire")
public class BibliothecaireController {
    @Autowired
    private BibliothecaireService bibliothecaireService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login-biblio";
    }

    @PostMapping("/login")
    public String login(@RequestParam String nom, 
                       @RequestParam String motDePasse,
                       HttpSession session,
                       Model model) {
        Bibliothecaire bibliothecaire = bibliothecaireService.authentifier(nom, motDePasse);
        if (bibliothecaire != null) {
            session.setAttribute("bibliothecaire", bibliothecaire);
            return "dashboard";
        } else {
            model.addAttribute("error", "Nom ou mot de passe incorrect");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}