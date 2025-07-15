package com.example.bibliotheque.services;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EtatExemplaireService {
    @Autowired
    private EtatExemplaireRepository repository;

    public List<EtatExemplaire> findAll() {
        return repository.findAll();
    }

    public Optional<EtatExemplaire> findById(Integer id) {
        return repository.findById(id);
    }

    public EtatExemplaire save(EtatExemplaire etat) {
        return repository.save(etat);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}