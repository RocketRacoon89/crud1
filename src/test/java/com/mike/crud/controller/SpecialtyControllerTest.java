package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SpecialtyControllerTest {

    @Mock
    SpecialtyController specialtyController;

    @Test
    public void createSpecialty() {
        Specialty testSpec = new Specialty();
        testSpec.setSpecialty("Ivan");
        testSpec.setStatus(Status.ACTIVE);
        specialtyController = Mockito.mock(SpecialtyController.class);
        when(specialtyController.createSpecialty("Ivan", "ACTIVE")).thenReturn(testSpec);
    }

    @Test
    public void updateSpecialty() {

    }

    @Test
    public void deleteSpecialty() {
    }

    @Test
    public void getAllSpecialty() {
    }

    @Test
    public void getSpecialty() {
    }
}