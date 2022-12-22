package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import com.mike.crud.services.SpecialtyService;

import java.util.List;

public class SpecialtyController {

//    private SpecialtyRepository specialtyRepository = new GsonSpecialtyRepositoryImpl();
//    private SpecialtyRepository specialtyRepository = new DbSpecialtyRepositoryImpl();
    private SpecialtyService specialtyService = new SpecialtyService();

    public Specialty createSpecialty(String name, String status) {
        Specialty specialty = new Specialty();
        specialty.setSpecialty(name);
        specialty.setStatus(Status.valueOf(status));
//        return specialtyRepository.save(specialty);
        return specialtyService.createSpecialtyService(specialty);
    }

    public Specialty updateSpecialty(Integer id, String name, String status) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setSpecialty(name);
        specialty.setStatus(Status.valueOf(status));
//        return specialtyRepository.update(specialty);
        return specialtyService.updateSpecialtyService(specialty);
    }

    public void deleteSpecialty(Integer id) {
//        specialtyRepository.deleteById(id);
        specialtyService.deleteSpecialtyService(id);
    }

    public List<Specialty> getAllSpecialty() {
//        return specialtyRepository.getAll();
        return specialtyService.getAllSpecialtyService();
    }

    public Specialty getSpecialty(Integer id) {
//        return specialtyRepository.getById(id);
        return specialtyService.getByIdSpecialtyService(id);
    }
}
