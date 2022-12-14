package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {

//    private SpecialtyRepository specialtyRepository = new GsonSpecialtyRepositoryImpl();
    private SpecialtyRepository specialtyRepository = new DbSpecialtyRepositoryImpl();

    public Specialty createSpecialty(String name, String status) {
        Specialty specialty = new Specialty();
        specialty.setSpecialty(name);
        specialty.setStatus(Status.valueOf(status));
        return specialtyRepository.save(specialty);
    }

    public Specialty updateSpecialty(Integer id, String name, String status) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setSpecialty(name);
        specialty.setStatus(Status.valueOf(status));
        return specialtyRepository.update(specialty);
    }

    public void deleteSpecialty(Integer id) {
        specialtyRepository.deleteById(id);
    }

    public List<Specialty> getAllSpecialty() {
        return specialtyRepository.getAll();
    }

    public Specialty getSpecialty(Integer id) {
        return specialtyRepository.getById(id);
    }
}
