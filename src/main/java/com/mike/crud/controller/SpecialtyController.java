package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
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
        return specialtyService.createSpecialty(specialty);
    }

    public Specialty updateSpecialty(Integer id, String name, String status) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setSpecialty(name);
        specialty.setStatus(Status.valueOf(status));
        return specialtyService.updateSpecialty(specialty);
    }

    public void deleteSpecialty(Integer id) {
        specialtyService.deleteSpecialty(id);
    }

    public List<Specialty> getAllSpecialty() {
        return specialtyService.getAllSpecialty();
    }

    public Specialty getSpecialty(Integer id) {
        return specialtyService.getByIdSpecialty(id);
    }
}
