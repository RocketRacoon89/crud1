package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import com.mike.crud.services.SpecialtyService;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpecialtyServiceTest {

    private SpecialtyRepository specialtyRepository = Mockito.mock(DbSpecialtyRepositoryImpl.class);
    private SpecialtyService specialtyService = new SpecialtyService(specialtyRepository);

    private Specialty getActiveSpecialty() {
        Specialty specialty = new Specialty();
        specialty.setId(1);
        specialty.setSpecialty("Test");
        specialty.setStatus(Status.ACTIVE);
        return specialty;
    }


    @Test
    public void createSpecialty() {
        Specialty specialtyToSave = new Specialty();
        specialtyToSave.setSpecialty("php");
        specialtyToSave.setStatus(Status.ACTIVE);
        when(specialtyRepository.save(any())).thenReturn(getActiveSpecialty());

        Specialty createdSpecialty = specialtyService.createSpecialty(specialtyToSave);
        assertEquals(createdSpecialty.getStatus(), Status.ACTIVE);
        assertNotNull(createdSpecialty.getId());
    }

    @Test
    public void failedCreateSpecialty() {
        Specialty specialtyToSave = new Specialty();
        specialtyToSave.setSpecialty("Java");
        specialtyToSave.setStatus(Status.ACTIVE);
        try {
            when(specialtyRepository.save(specialtyToSave)).thenThrow(new SQLException("SQL Exception"));
            specialtyService.createSpecialty(specialtyToSave);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void updateSpecialty() {
        Specialty specialtyToUpdate = new Specialty();
        specialtyToUpdate.setSpecialty("php");
        specialtyToUpdate.setStatus(Status.ACTIVE);
        when(specialtyRepository.update(any())).thenReturn(getActiveSpecialty());

        Specialty updateSpecialty = specialtyService.updateSpecialty(specialtyToUpdate);
        assertEquals(updateSpecialty.getStatus(),Status.ACTIVE);
        assertNotNull(updateSpecialty.getId());
    }

    @Test
    public void failedUpdateSpecialty() {
        Specialty specialtyToUpdate = new Specialty();
        specialtyToUpdate.setSpecialty("php");
        specialtyToUpdate.setStatus(Status.ACTIVE);
        try{
            when(specialtyRepository.update(any())).thenThrow(new SQLException("SQL Exception"));
            specialtyService.updateSpecialty(specialtyToUpdate);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void deleteSpecialty() {
        specialtyService.deleteSpecialty(4321);
        Mockito.verify(specialtyRepository, Mockito.times(1)).deleteById(any());
    }

    @Test
    public void failedDeleteSpecialty() {
        try{
            Mockito.doThrow(new NullPointerException()).when(specialtyRepository).deleteById(isA(Integer.class));
            specialtyService.deleteSpecialty(any());
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void getAllSpecialty() {
        List<Specialty> specList = new ArrayList<>();
        specList.add(getActiveSpecialty());
        when(specialtyRepository.getAll()).thenReturn(specList);
        List<Specialty> allSpec = specialtyService.getAllSpecialty();
        assertNotNull(allSpec.get(0).getId());
    }

    @Test
    public void failedGetAllSpecialty() {
        try {
            when(specialtyRepository.getAll()).thenThrow(new SQLException("SQL Exception"));
            specialtyService.getAllSpecialty();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("SQL Exception"));
        }
    }

    @Test
    public void getSpecialty() {
        when(specialtyRepository.getById(any())).thenReturn(getActiveSpecialty());
        Specialty returnSpecialty = specialtyService.getByIdSpecialty(isA(Integer.class));
        assertNotNull(returnSpecialty.getId());
    }

    @Test
    public void failedGetSpecialty() {
        try{
            when(specialtyRepository.getById(any())).thenThrow(new NullPointerException());
            specialtyService.getByIdSpecialty(any());
        } catch (NullPointerException e) {
            assertTrue(e instanceof NullPointerException);
        }
    }
}