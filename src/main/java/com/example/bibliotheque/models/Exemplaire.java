package com.example.bibliotheque.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "exemplaire")
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @OneToMany(mappedBy = "exemplaire", fetch = FetchType.LAZY)
    private List<StatutExemplaire> statuts;

    // Getters / Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }


    public List<StatutExemplaire> getStatuts() {
        return statuts;
    }

    public void setStatuts(List<StatutExemplaire> statuts) {
        this.statuts = statuts;
    }
}
