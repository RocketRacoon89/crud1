package com.mike.crud.services;

import com.mike.crud.model.Developer;
import com.mike.crud.repository.DeveloperRepository;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.repository.database.DbDeveloperRepositoryImpl;
import com.mike.crud.repository.database.DbSkillRepositoryImpl;

import java.util.List;

public class DeveloperService {

    private final DeveloperRepository developerRepository;

    public DeveloperService() {
        this.developerRepository = new DbDeveloperRepositoryImpl();
    }

    public DeveloperService(DeveloperRepository dbDeveloperRepository) {
        this.developerRepository = dbDeveloperRepository;
    }

    public Developer createDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

    public Developer updateDeveloper(Developer developer) {
        return developerRepository.update(developer);
    }

    public void deleteDeveloper(Integer id) {
        developerRepository.deleteById(id);
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.getAll();
    }

    public Developer getDeveloperById(Integer id) {
        return developerRepository.getById(id);
    }
}
