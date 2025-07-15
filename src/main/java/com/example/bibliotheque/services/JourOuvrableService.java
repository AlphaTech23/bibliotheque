package com.example.bibliotheque.services;

import com.example.bibliotheque.models.*;
import com.example.bibliotheque.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JourOuvrableService {
    @Autowired
    private JourOuvrableRepository repository;

    public List<JourOuvrable> findAll() {
        return repository.findAll();
    }

    public Optional<JourOuvrable> findById(Integer id) {
        return repository.findById(id);
    }

    public JourOuvrable save(JourOuvrable jourOuvrable) {
        return repository.save(jourOuvrable);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Optional<JourOuvrable> findByJourSemaine(int jourSemaine) {
        return repository.findByJourSemaine(jourSemaine);
    }
}
