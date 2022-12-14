package com.mike.crud.view;

import com.mike.crud.controller.SpecialtyController;
import com.mike.crud.model.Specialty;

import java.util.Scanner;

public class SpecialtyView {
    private final Scanner scanner = new Scanner(System.in);
    private final SpecialtyController specialtyController = new SpecialtyController();

    public void createSpecialty() {
        System.out.println("Enter name Specialty:");
        String name = scanner.nextLine();
        System.out.println("Enter Specialty status:");
        String status = scanner.nextLine();

        Specialty specialty = specialtyController.createSpecialty(name, status.toUpperCase());
        System.out.println("Created specialty "+specialty);
    }

    public void updateSpecialty() {
        System.out.println("Enter ID Specialty:");
        Integer ID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter name Specialty:");
        String name = scanner.nextLine();

        System.out.println("Enter Specialty status:");
        String status = scanner.nextLine();

        Specialty specialty = specialtyController.updateSpecialty(ID, name, status.toUpperCase());
        System.out.println("Updated Specialty"+specialty);
    }

    public void deleteSpecialty() {
        System.out.println("Enter ID Specialty for delete:");
        Integer ID = scanner.nextInt();
        specialtyController.deleteSpecialty(ID);
        System.out.println("Specialty ID: "+ID+" Deleted");
    }

    public void viewSpecialties() {
        System.out.println(specialtyController.getAllSpecialty());
    }

    public void viewSpecialty() {
        System.out.println("Enter ID Specialty:");
        Integer ID = scanner.nextInt();
        System.out.println(specialtyController.getSpecialty(ID));
    }
}
