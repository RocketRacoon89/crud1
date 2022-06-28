package com.Mike.crud.controller;

import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.SpecialtyRepository;
import com.Mike.crud.repository.gson.GsonSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {

    private SpecialtyRepository specialtyRepository = new GsonSpecialtyRepositoryImpl();

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
