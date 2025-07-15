package com.example.bibliotheque.models;

import jakarta.persistence.*;

@Entity
@Table(name = "jour_ouvrable")
public class JourOuvrable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer jourSemaine;
    private boolean avant;
    
    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getJourSemaine() { return jourSemaine; }
    public void setJourSemaine(Integer jourSemaine) { this.jourSemaine = jourSemaine; }
    
    public boolean isAvant() { return avant; }
    public void setAvant(boolean avant) { this.avant = avant; }
}