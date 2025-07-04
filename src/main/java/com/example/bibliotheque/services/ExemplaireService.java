package com.example.bibliotheque.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.models.Exemplaire;
import com.example.bibliotheque.repositories.ExemplaireRepository;
import com.example.bibliotheque.repositories.PretRepository;
import com.example.bibliotheque.repositories.ReservationRepository;

@Service
public class ExemplaireService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Exemplaire> findAll() {
        return exemplaireRepository.findAll();
    }

    public Exemplaire findById(Integer id) {
        return exemplaireRepository.findById(id).orElse(null);
    }

    public boolean isLivreDisponible(Integer livreId, LocalDate dateEmprunt) {
        return false;
    }
}
