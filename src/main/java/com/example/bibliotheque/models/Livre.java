package com.example.bibliotheque.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private Integer restriction;

    @OneToMany(mappedBy = "livre")
    private List<Exemplaire> exemplaires;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Integer getRestriction() { return restriction; }
    public void setRestriction(Integer restriction) { this.restriction = restriction; }

    public List<Exemplaire> getExemplaires() { return exemplaires; }
    public void setExemplaires(List<Exemplaire> exemplaires) { this.exemplaires = exemplaires; }
}
