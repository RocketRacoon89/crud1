package com.mike.crud.services;

import com.mike.crud.model.Skill;
import com.mike.crud.model.Specialty;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbSkillRepositoryImpl;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;

import java.util.List;

public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService() {
        this.skillRepository = new DbSkillRepositoryImpl();
    }

    public SkillService(SkillRepository dbSkillRepository) {
        this.skillRepository = dbSkillRepository;
    }

    public Skill createSpecialty(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSpecialty(Skill skill) {
        return skillRepository.update(skill);
    }

    public void deleteSpecialty(Integer id) {
        skillRepository.deleteById(id);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill getByIdSpecialty(Integer id) {
        return skillRepository.getById(id);
    }
}
