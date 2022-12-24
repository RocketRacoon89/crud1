package com.mike.crud.controller;

import com.mike.crud.model.Developer;
import com.mike.crud.model.Skill;
import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.DeveloperRepository;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbDeveloperRepositoryImpl;
import com.mike.crud.repository.database.DbSkillRepositoryImpl;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import com.mike.crud.services.DeveloperService;

import java.util.ArrayList;
import java.util.List;

public class DeveloperController {

//    private final DeveloperRepository developerRepository = new GsonDeveloperRepositoryImpl();
//    private final SkillRepository skillRepository = new GsonSkillRepositoryImpl();
//    private final SpecialtyRepository specialtyRepository = new GsonSpecialtyRepositoryImpl();

    private final DeveloperRepository developerRepository = new DbDeveloperRepositoryImpl();
    private final SkillRepository skillRepository = new DbSkillRepositoryImpl();
    private final SpecialtyRepository specialtyRepository = new DbSpecialtyRepositoryImpl();
    private DeveloperService developerService = new DeveloperService();

    public Developer createDeveloper(String firstName, String lastName, List<Integer> skillID, Integer specialty, String status) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        List<Skill> skills = new ArrayList<>();
        skillID.stream().forEach(s-> skills.add(skillRepository.getById(s)));
        developer.setSkills(skills);
        developer.setSpecialty(specialtyRepository.getById(specialty));
        developer.setStatus(Status.valueOf(status));
        return developerService.createDeveloper(developer);
    }

    public Developer updateDeveloper(Integer id, String firstName, String lastName, List<Integer> skillID, Integer specialty, String status) {
        Developer developer = new Developer();
        developer.setId(id);
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        List<Skill> skills = new ArrayList<>();
        skillID.stream().forEach(s-> skills.add(skillRepository.getById(s)));
        developer.setSkills(skills);
        developer.setSpecialty(specialtyRepository.getById(specialty));
        developer.setStatus(Status.valueOf(status));
        return developerService.updateDeveloper(developer);
    }

    public void deleteDeveloper(Integer id) {
        developerService.deleteDeveloper(id);
    }

    public List<Developer> getAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    public Developer getDeveloper(Integer id) {
        return developerService.getDeveloperById(id);
    }

}