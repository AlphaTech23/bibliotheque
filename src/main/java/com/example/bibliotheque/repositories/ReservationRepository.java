package com.example.bibliotheque.repositories;

import java.time.LocalDate;

import com.example.bibliotheque.models.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByExemplaireIdAndDateReservation(Integer exemplaireId, LocalDate dateReservation);
    long countByAdherentId(Integer adherentId);
}
