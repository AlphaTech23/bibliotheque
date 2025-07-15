package com.example.bibliotheque.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "jour_ferier")
public class JourFerier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private LocalDate dateFerier;
    private String evenement;
    private boolean avant;
    
    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public LocalDate getDateFerier() { return dateFerier; }
    public void setDateFerier(LocalDate dateFerier) { this.dateFerier = dateFerier; }
    
    public String getEvenement() { return evenement; }
    public void setEvenement(String evenement) { this.evenement = evenement; }
    
    public boolean isAvant() { return avant; }
    public void setAvant(boolean avant) { this.avant = avant; }
}