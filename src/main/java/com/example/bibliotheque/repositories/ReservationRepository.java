package com.example.bibliotheque.repositories;

import java.time.LocalDate;
import java.util.List;

import com.example.bibliotheque.models.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByExemplaireIdAndDateReservation(Integer exemplaireId, LocalDate dateReservation);

    long countByAdherentIdAndValideIsNull(Integer adherentId);

    long countByAdherentIdAndValide(Integer adherentId, Boolean valid);

    List<Reservation> findAllByOrderByDateReservationDesc();

    @Query("""
            SELECT r FROM Reservation r
            WHERE (:nom IS NULL OR
                    LOWER(r.adherent.nom) LIKE LOWER(CONCAT('%', :nom, '%')) OR
                    LOWER(r.adherent.email) LIKE LOWER(CONCAT('%', :nom, '%')) OR
                    LOWER(r.exemplaire.livre.nom) LIKE LOWER(CONCAT('%', :livre, '%')))
            AND (:dateDebut IS NULL OR r.dateReservation >= :dateDebut)
            AND (:dateFin IS NULL OR r.dateReservation <= :dateFin)
            AND (:valide IS NULL OR r.valide = :valide)
            ORDER BY r.dateReservation DESC
            """)
    List<Reservation> search(
            @Param("nom") String nom,
            @Param("livre") String livre,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("valide") Boolean valide);

}
