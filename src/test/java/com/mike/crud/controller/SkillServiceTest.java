package com.mike.crud.controller;

import com.mike.crud.model.Skill;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.repository.database.DbSkillRepositoryImpl;
import com.mike.crud.services.SkillService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class SkillServiceTest {


    private SkillRepository skillRepository = Mockito.mock(DbSkillRepositoryImpl.class);
    private SkillService skillService = new SkillService(skillRepository);

    private Skill getActiveSkill() {
        Skill skill = new Skill();
        skill.setId(1);
        skill.setSkill("Test");
        skill.setStatus(Status.ACTIVE);
        return skill;
    }


    @Test
    public void createSkill() {
        Skill skillToSave = new Skill();
        skillToSave.setSkill("Spring");
        skillToSave.setStatus(Status.ACTIVE);
        when(skillRepository.save(any())).thenReturn(getActiveSkill());

        Skill createdSkill = skillService.createSpecialty(skillToSave);
        assertEquals(createdSkill.getStatus(), Status.ACTIVE);
        assertNotNull(createdSkill.getId());
    }

    @Test
    public void updateSkill() {
        Skill skillToUpdate = new Skill();
        skillToUpdate.setSkill("Spring");
        skillToUpdate.setStatus(Status.ACTIVE);
        when(skillRepository.save(any())).thenReturn(getActiveSkill());

        Skill updateSkill = skillService.updateSpecialty(skillToUpdate);
        assertEquals(updateSkill.getStatus(),Status.ACTIVE);
        assertNotNull(updateSkill.getId());
    }

    @Test
    public void deleteSkill() {
        doNothing().when(skillRepository).deleteById(any());
        skillService.deleteSpecialty(isA(Integer.class));
    }

    @Test
    public void getAllSkills() {
        List<Skill> skillList = new ArrayList<>();
        skillList.add(getActiveSkill());
        when(skillRepository.getAll()).thenReturn(skillList);
        List<Skill> allSkills = skillService.getAllSkills();
        assertNotNull(allSkills.get(0).getId());
    }

    @Test
    public void getSkill() {
        when(skillRepository.getById(any())).thenReturn(getActiveSkill());
        Skill returnSkill = skillService.getByIdSpecialty(isA(Integer.class));
        assertNotNull(returnSkill.getId());
    }
}
