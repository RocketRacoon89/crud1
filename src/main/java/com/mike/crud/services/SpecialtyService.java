package com.mike.crud.services;

import com.mike.crud.model.Specialty;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService() {
        this.specialtyRepository = new DbSpecialtyRepositoryImpl();
    }

    public SpecialtyService(SpecialtyRepository dbSpecialtyRepository) {
        this.specialtyRepository = dbSpecialtyRepository;
    }

    public Specialty createSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty updateSpecialty(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    public void deleteSpecialty(Integer id) {
        specialtyRepository.deleteById(id);
    }

    public List<Specialty> getAllSpecialty() {
        return specialtyRepository.getAll();
    }

    public Specialty getByIdSpecialty(Integer id) {
        return specialtyRepository.getById(id);
    }
}
