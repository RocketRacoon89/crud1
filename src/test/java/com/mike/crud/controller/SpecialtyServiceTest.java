package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SpecialtyRepository;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import com.mike.crud.services.SpecialtyService;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        specialtyService.deleteSpecialty(isA(Integer.class));
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