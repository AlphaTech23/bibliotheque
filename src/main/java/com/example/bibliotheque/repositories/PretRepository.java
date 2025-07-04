package com.example.bibliotheque.repositories;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bibliotheque.models.Pret;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
       long countByAdherentIdAndDateRetourIsNullAndTypePret_LibelleNot(Integer adherentId, String typePret);

       @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Pret p " +
              "WHERE p.exemplaire.id = :exId " +
              "AND p.datePret <= :d " +
              "AND p.dateRetour IS NULL")
       boolean existsPretActifSurExemplaire(@Param("exId") Integer exemplaireId);
       Optional<Pret> findByExemplaireIdAndDateRetourIsNull(Integer exemplaireId);
}
