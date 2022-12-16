package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;

import static org.junit.Assert.*;

public class SpecialtyControllerTest {

    DbSpecialtyRepositoryImpl dbSpecialtyRepository;

    @Test
    public void createSpecialty() {
        Specialty specialty = new Specialty();
        specialty.setId(743);
        specialty.setSpecialty("C#");
        specialty.setStatus(Status.ACTIVE);
        int actual = specialty.getId();
        int expected = 743;
        assertEquals(expected, actual);
        assertEquals("C#", specialty.getSpecialty());
        assertEquals(Status.ACTIVE, specialty.getStatus());
    }

    @Test
    public void updateSpecialty() {
        Specialty specialty = new Specialty();
        specialty.setId(1);
        specialty.setSpecialty("C#");
        specialty.setStatus(Status.ACTIVE);
        int actual = specialty.getId();
        int expected = 1;
        assertEquals(expected, actual);
        assertEquals("C#", specialty.getSpecialty());
        assertEquals(Status.ACTIVE, specialty.getStatus());
    }

    @Test
    public void deleteSpecialty() {
    }

    @Test
    public void getAllSpecialty() {
    }

    @Test
    public void getSpecialty() {
        int id = 2;
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setSpecialty("C#");
        specialty.setStatus(Status.ACTIVE);
        Specialty specialtyExp = (Specialty) Mockito.when(dbSpecialtyRepository.getById(2)).thenReturn(specialty);
        assertNotNull(specialtyExp);
    }
}