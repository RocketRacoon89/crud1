package com.mike.crud.services;

import com.mike.crud.model.Specialty;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private DbSpecialtyRepositoryImpl dbSpecialtyRepository = new DbSpecialtyRepositoryImpl();


    public void setDbSpecialtyRepository(DbSpecialtyRepositoryImpl dbSpecialtyRepository) {
        this.dbSpecialtyRepository = dbSpecialtyRepository;
    }

    public Specialty createSpecialtyService(Specialty specialty) {
        return dbSpecialtyRepository.save(specialty);
    }

    public Specialty updateSpecialtyService(Specialty specialty) {
        return dbSpecialtyRepository.update(specialty);
    }

    public void deleteSpecialtyService(Integer id) {
        dbSpecialtyRepository.deleteById(id);
    }

    public List<Specialty> getAllSpecialtyService() {
        return dbSpecialtyRepository.getAll();
    }

    public Specialty getByIdSpecialtyService(Integer id) {
        return dbSpecialtyRepository.getById(id);
    }
}
