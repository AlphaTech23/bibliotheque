package com.example.bibliotheque.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bibliotheque.models.Pret;

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

       @Query("SELECT p FROM Pret p " +
                     "WHERE (:adherentId IS NULL OR p.adherent.id = :adherentId) " +
                     "AND (:typePretId IS NULL OR p.typePret.id = :typePretId) " +
                     "AND (:dateDebut IS NULL OR p.datePret >= :dateDebut) " +
                     "AND (:dateFin IS NULL OR p.datePret <= :dateFin) " +
                     "AND (:nom IS NULL OR LOWER(p.adherent.nom) LIKE LOWER(CONCAT('%', :nom, '%')) " +
                     "     OR LOWER(p.adherent.email) LIKE LOWER(CONCAT('%', :nom, '%')) " +
                     "     OR LOWER(p.exemplaire.livre.nom) LIKE LOWER(CONCAT('%', :nom, '%')))")
       List<Pret> searchPretMulticritere(
                     @Param("adherentId") Integer adherentId,
                     @Param("typePretId") Integer typePretId,
                     @Param("dateDebut") LocalDate dateDebut,
                     @Param("dateFin") LocalDate dateFin,
                     @Param("nom") String nom);
}
