package com.example.bibliotheque.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "type_adherent")
public class TypeAdherent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    @Column(name = "quota_exemplaire", nullable = false)
    private Integer quotaExemplaire;

    @Column(name = "duree_pret", nullable = false)
    private Integer dureePret;

    @Column(name = "duree_prolongement", nullable = false)
    private Integer dureeProlongement;

    @Column(name = "duree_penalite", nullable = false)
    private Integer dureePenalite;

    @Column(name = "quota_reservation", nullable = false)
    private Integer quotaReservation;

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getQuotaExemplaire() {
        return quotaExemplaire;
    }

    public void setQuotaExemplaire(Integer quotaExemplaire) {
        this.quotaExemplaire = quotaExemplaire;
    }

    public Integer getDureePret() {
        return dureePret;
    }

    public void setDureePret(Integer dureePret) {
        this.dureePret = dureePret;
    }

    public Integer getDureeProlongement() {
        return dureeProlongement;
    }

    public void setDureeProlongement(Integer dureeProlongement) {
        this.dureeProlongement = dureeProlongement;
    }

    public Integer getDureePenalite() {
        return dureePenalite;
    }

    public void setDureePenalite(Integer dureePenalite) {
        this.dureePenalite = dureePenalite;
    }

    public Integer getQuotaReservation() {
        return quotaReservation;
    }

    public void setQuotaReservation(Integer quotaReservation) {
        this.quotaReservation = quotaReservation;
    }
}
