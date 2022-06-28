package com.Mike.crud.controller;

import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.SkillRepository;
import com.Mike.crud.repository.gson.GsonSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository = new GsonSkillRepositoryImpl();

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
