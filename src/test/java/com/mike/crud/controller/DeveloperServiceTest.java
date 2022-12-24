package com.mike.crud.controller;

import com.mike.crud.model.Developer;
import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.DeveloperRepository;
import com.mike.crud.repository.database.DbDeveloperRepositoryImpl;
import com.mike.crud.services.DeveloperService;
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
    public void updateDeveloper() {
        Developer developer = new Developer();
        developer.setId(1);
        developer.setFirstName("Test");
        developer.setLastName("Test");
        developer.setSkills(new ArrayList<>());
        developer.setSpecialty(new Specialty());
        developer.setStatus(Status.ACTIVE);
        when(developerRepository.update(any())).thenReturn(getTestDeveloper());

        Developer updateDeveloper = developerService.createDeveloper(developer);
        assertEquals(updateDeveloper.getStatus(),Status.ACTIVE);
        assertNotNull(updateDeveloper.getId());
    }

    @Test
    public void deleteDeveloper() {
        doNothing().when(developerRepository).deleteById(any());
        developerService.deleteDeveloper(isA(Integer.class));
    }

    @Test
    public void getAllDevelopers() {
        List<Developer> developerList = new ArrayList<>();
        developerList.add(getTestDeveloper());
        when(developerRepository.getAll()).thenReturn(developerList);
        assertNotNull(developerList.get(0).getId());
    }

    @Test
    public void getDeveloperById() {
        when(developerRepository.getById(any())).thenReturn(getTestDeveloper());
        Developer developer = developerService.getDeveloperById(isA(Integer.class));
        assertNotNull(developer.getId());
    }
}
