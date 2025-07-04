package com.example.bibliotheque.services;

import com.example.bibliotheque.models.TypeAdherent;
import com.example.bibliotheque.repositories.TypeAdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeAdherentService {

    @Autowired
    private TypeAdherentRepository typeAdherentRepository;

    public List<TypeAdherent> findAll() {
        return typeAdherentRepository.findAll();
    }
}
