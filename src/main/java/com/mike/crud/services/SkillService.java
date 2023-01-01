package com.mike.crud.services;

import com.mike.crud.model.Skill;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.repository.database.DbSkillRepositoryImpl;

import java.util.List;

public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService() {
        this.skillRepository = new DbSkillRepositoryImpl();
    }

    public SkillService(SkillRepository dbSkillRepository) {
        this.skillRepository = dbSkillRepository;
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Skill skill) {
        return skillRepository.update(skill);
    }

    public void deleteSkill(Integer id) {
        skillRepository.deleteById(id);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill getByIdSkill(Integer id) {
        return skillRepository.getById(id);
    }
}
