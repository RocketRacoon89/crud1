package com.mike.crud.controller;

import com.mike.crud.model.Developer;
import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.DeveloperRepository;
import com.mike.crud.repository.database.DbDeveloperRepositoryImpl;
import com.mike.crud.services.DeveloperService;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

public class DeveloperServiceTest {
    private DeveloperRepository developerRepository = Mockito.mock(DbDeveloperRepositoryImpl.class);
    private DeveloperService developerService = new DeveloperService(developerRepository);

    private Developer getTestDeveloper() {
        Developer developer = new Developer();
        developer.setId(1);
        developer.setFirstName("Test");
        developer.setLastName("Test");
        developer.setSkills(new ArrayList<>());
        developer.setSpecialty(new Specialty());
        developer.setStatus(Status.ACTIVE);
        return developer;
    }

    @Test
    public void createDeveloper() {
        Developer developer = new Developer();
        developer.setId(1);
        developer.setFirstName("Test");
        developer.setLastName("Test");
        developer.setSkills(new ArrayList<>());
        developer.setSpecialty(new Specialty());
        developer.setStatus(Status.ACTIVE);
        when(developerRepository.save(any())).thenReturn(getTestDeveloper());

        Developer createdDeveloper = developerService.createDeveloper(developer);
        assertEquals(createdDeveloper.getStatus(),Status.ACTIVE);
        assertNotNull(createdDeveloper.getId());
    }

    @Test
    public void failedCreateDeveloper() {
        Developer developer = new Developer();
        developer.setId(1);
        developer.setFirstName("Test");
        developer.setLastName("Test");
        developer.setSkills(new ArrayList<>());
        developer.setSpecialty(new Specialty());
        developer.setStatus(Status.ACTIVE);

        try{
            when(developerRepository.save(any())).thenThrow(new SQLException("SQL Exception"));
            developerService.createDeveloper(developer);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void updateDeveloper() {
        Developer developer = new Developer();
        developer.setId(1);
        developer.setFirstName("Test");
        developer.setLastName("Test");
        developer.setSkills(new ArrayList<>());
        developer.setSpecialty(new Specialty());
        developer.setStatus(Status.ACTIVE);
        when(developerRepository.update(any())).thenReturn(getTestDeveloper());

        Developer updateDeveloper = developerService.updateDeveloper(developer);
        assertEquals(updateDeveloper.getStatus(),Status.ACTIVE);
        assertNotNull(updateDeveloper.getId());
    }

    @Test
    public void failedUpdateDeveloper() {
        Developer developer = new Developer();
        developer.setId(1);
        developer.setFirstName("Test");
        developer.setLastName("Test");
        developer.setSkills(new ArrayList<>());
        developer.setSpecialty(new Specialty());
        developer.setStatus(Status.ACTIVE);

        try{
            when(developerRepository.update(any())).thenThrow(new SQLException("SQL Exception"));
            developerService.createDeveloper(developer);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void deleteDeveloper() {
        developerService.deleteDeveloper(isA(Integer.class));
        Mockito.verify(developerRepository, Mockito.times(1)).deleteById(isA(Integer.class));
    }

    @Test
    public void failedDeleteDeveloper() {
        try{
            Mockito.doThrow(new SQLException("SQL Exception")).when(developerRepository).deleteById(isA(Integer.class));
            developerService.deleteDeveloper(isA(Integer.class));
        }catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }


    }

    @Test
    public void getAllDevelopers() {
        List<Developer> developerList = new ArrayList<>();
        developerList.add(getTestDeveloper());
        when(developerRepository.getAll()).thenReturn(developerList);
        assertNotNull(developerList.get(0).getId());
    }

    @Test
    public void failedGetAllDeveloper() {
        try{
            when(developerRepository.getAll()).thenThrow(new NullPointerException());
            developerService.getAllDevelopers();
        }catch (NullPointerException e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void getDeveloperById() {
        when(developerRepository.getById(any())).thenReturn(getTestDeveloper());
        Developer developer = developerService.getDeveloperById(isA(Integer.class));
        assertNotNull(developer.getId());
    }

    @Test
    public void failedGetDeveloperById() {
        try{
            when(developerRepository.getById(isA(Integer.class))).thenThrow(new NullPointerException());
            developerService.getDeveloperById(isA(Integer.class));
        }catch (NullPointerException e) {
            assertTrue(e instanceof NullPointerException);
        }
    }
}
