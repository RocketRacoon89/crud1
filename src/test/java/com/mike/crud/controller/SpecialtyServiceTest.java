package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import com.mike.crud.services.SpecialtyService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpecialtyServiceTest {

    private SpecialtyRepository specialtyRepository = Mockito.mock(DbSpecialtyRepositoryImpl.class);
    private SpecialtyService specialtyService = new SpecialtyService(specialtyRepository);

    @Spy
    @InjectMocks
    private SpecialtyService specialtyServiceSpy = new SpecialtyService();

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
//        Specialty specialtyToSave = new Specialty();
//        specialtyToSave.setSpecialty("");
//        specialtyToSave.setStatus(Status.ACTIVE);
//        when(specialtyRepository.save(specialtyToSave)).
//        System.out.println(specialtyToSave.getSpecialty().equals(null));
//
//        Specialty createdSpecialty = specialtyService.createSpecialty(specialtyToSave);
//        assertNull(createdSpecialty.getId());
    }

    @Test
    public void updateSpecialty() {
        Specialty specialtyToUpdate = new Specialty();
        specialtyToUpdate.setSpecialty("php");
        specialtyToUpdate.setStatus(Status.ACTIVE);
        when(specialtyRepository.save(any())).thenReturn(getActiveSpecialty());

        Specialty updateSpecialty = specialtyService.updateSpecialty(specialtyToUpdate);
        assertEquals(updateSpecialty.getStatus(),Status.ACTIVE);
        assertNotNull(updateSpecialty.getId());
    }

    @Test
    public void deleteSpecialty() {
        doNothing().when(specialtyRepository).deleteById(any());
        specialtyServiceSpy.deleteSpecialty(4321);
        Mockito.verify(specialtyServiceSpy, Mockito.times(1)).deleteSpecialty(4321);
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
    public void getSpecialty() {
        when(specialtyRepository.getById(any())).thenReturn(getActiveSpecialty());
        Specialty returnSpecialty = specialtyService.getByIdSpecialty(isA(Integer.class));
        assertNotNull(returnSpecialty.getId());
    }
}