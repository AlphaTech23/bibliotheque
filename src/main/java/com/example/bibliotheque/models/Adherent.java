package com.example.bibliotheque.models;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "adherent")
public class Adherent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private TypeAdherent typeAdherent;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Integer getAge() {
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public TypeAdherent getTypeAdherent() {
        return typeAdherent;
    }

    public void setTypeAdherent(TypeAdherent typeAdherent) {
        this.typeAdherent = typeAdherent;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
