package com.mike.crud.controller;

import com.mike.crud.model.Skill;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.repository.database.DbSkillRepositoryImpl;

import java.util.List;

public class SkillController {
//    private final SkillRepository skillRepository = new GsonSkillRepositoryImpl();
private final SkillRepository skillRepository = new DbSkillRepositoryImpl();

    public Skill createSkill(String name, String status) {
        Skill skill = new Skill();
        skill.setSkill(name);
        skill.setStatus(Status.valueOf(status));
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Integer id, String name, String status) {
        Skill skill = new Skill();
        skill.setId(id);
        skill.setSkill(name);
        skill.setStatus(Status.valueOf(status));
        return skillRepository.update(skill);
    }

    public void deleteSkill(Integer id) {
        skillRepository.deleteById(id);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill getSkill(Integer id) {
        return skillRepository.getById(id);
    }

}
