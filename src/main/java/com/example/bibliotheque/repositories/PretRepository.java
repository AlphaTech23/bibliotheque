package com.example.bibliotheque.repositories;

import java.util.Optional;
import java.util.List;

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

       boolean existsByExemplaireIdAndDateRetourIsNull(Integer exemplaireId);

       // Retourne la liste des prêts actifs pour tous les exemplaires d'un livre donné
       @Query("SELECT p FROM Pret p WHERE p.exemplaire.livre.id = :livreId AND p.dateRetour IS NULL")
       List<Pret> findActifsByLivreId(@Param("livreId") Integer livreId);
}
