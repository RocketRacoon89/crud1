package com.mike.crud.controller;

import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.database.DbSpecialtyRepositoryImpl;
import com.mike.crud.services.SpecialtyService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SpecialtyControllerTest {

//    @Mock
//    SpecialtyController specialtyController;

    @Mock
    DbSpecialtyRepositoryImpl dbSpecialtyRepository;

    @Test
    public void createSpecialty() {
        Specialty testSpec = new Specialty();
        testSpec.setSpecialty("Php");
        testSpec.setStatus(Status.ACTIVE);
//        specialtyController = Mockito.mock(SpecialtyController.class);
//        when(specialtyController.createSpecialty(testSpec.getSpecialty(), testSpec.getStatus().toString())).thenReturn(testSpec);
        dbSpecialtyRepository = Mockito.mock(DbSpecialtyRepositoryImpl.class);
        SpecialtyService service = new SpecialtyService();
        service.setDbSpecialtyRepository(dbSpecialtyRepository);
        service.createSpecialtyService(testSpec);

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