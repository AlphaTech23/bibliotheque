package com.example.bibliotheque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.models.TypeAdherent;
import com.example.bibliotheque.repositories.TypeAdherentRepository;

@Service
public class TypeAdherentService {

    @Autowired
    private TypeAdherentRepository typeAdherentRepository;

    public List<TypeAdherent> findAll() {
        return typeAdherentRepository.findAll();
    }

    public TypeAdherent findById(Integer id) throws Exception {
        return typeAdherentRepository.findById(id).orElseThrow(() -> new Exception("TypeAdherent introuvable."));
    }
}
