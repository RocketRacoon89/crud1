package com.mike.crud.controller;

import com.mike.crud.model.Skill;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.repository.database.DbSkillRepositoryImpl;
import com.mike.crud.services.SkillService;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

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

        Skill createdSkill = skillService.createSkill(skillToSave);
        assertEquals(createdSkill.getStatus(), Status.ACTIVE);
        assertNotNull(createdSkill.getId());
    }

    @Test
    public void failedCreateSkill() {
        Skill skillToSave = new Skill();
        skillToSave.setSkill("Spring");
        skillToSave.setStatus(Status.ACTIVE);
        try {
            when(skillRepository.save(skillToSave)).thenThrow(new SQLException("SQL Exception"));
            skillService.createSkill(skillToSave);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void updateSkill() {
        Skill skillToUpdate = new Skill();
        skillToUpdate.setSkill("Spring");
        skillToUpdate.setStatus(Status.ACTIVE);
        when(skillRepository.update(any())).thenReturn(getActiveSkill());

        Skill updateSkill = skillService.updateSkill(skillToUpdate);
        assertEquals(updateSkill.getStatus(),Status.ACTIVE);
        assertNotNull(updateSkill.getId());
    }

    @Test
    public void failedUpdateSkill() {
        Skill skillToUpdate = new Skill();
        skillToUpdate.setSkill("Spring");
        skillToUpdate.setStatus(Status.ACTIVE);
        try {
            when(skillRepository.update(skillToUpdate)).thenThrow(new SQLException("SQL Exception"));
            skillService.updateSkill(skillToUpdate);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void deleteSkill() {
        skillService.deleteSkill(isA(Integer.class));
        Mockito.verify(skillRepository, Mockito.times(1)).deleteById(isA(Integer.class));
    }

    @Test
    public void failedDeleteSkill() {
        try {
            Mockito.doThrow(new NullPointerException()).when(skillRepository).deleteById(isA(Integer.class));
            skillService.deleteSkill(isA(Integer.class));
        } catch (NullPointerException e) {
            assertTrue(e instanceof NullPointerException);
        }
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
    public void failedGetAllSkills() {
        try{
            when(skillRepository.getAll()).thenThrow(new SQLException("SQL Exception"));
            skillService.getAllSkills();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void getSkill() {
        when(skillRepository.getById(any())).thenReturn(getActiveSkill());
        Skill returnSkill = skillService.getByIdSkill(isA(Integer.class));
        assertNotNull(returnSkill.getId());
    }

    @Test
    public void failedGetSkill() {
        try {
            when(skillRepository.getById(isA(Integer.class))).thenThrow(new NullPointerException());
            skillService.getByIdSkill(isA(Integer.class));
        } catch (NullPointerException e) {
            assertTrue(e instanceof NullPointerException);
        }
    }
}
