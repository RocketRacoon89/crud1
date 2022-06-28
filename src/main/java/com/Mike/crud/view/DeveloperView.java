package com.Mike.crud.view;

import com.Mike.crud.controller.DeveloperController;
import com.Mike.crud.model.Developer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeveloperView {

    private final Scanner scanner = new Scanner(System.in);
    private final DeveloperController developerController = new DeveloperController();

    public void createDeveloper() {
        System.out.println("Enter FirstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter LastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter ID skill for developer");
        List <Integer> listSkill = new ArrayList<>();
        String command = "y";
        while(command.equals("y")) {
            Integer idSkill = scanner.nextInt();
            scanner.nextLine();
            listSkill.add(idSkill);
            System.out.println("Enter \"y\" for add skill or \"n\" for go next");
            command = scanner.nextLine();
        }

        System.out.println("Enter ID specialty for developer");
        Integer idSpec = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter status(ACTIVE or DELETED)");
        String status = scanner.nextLine();

        Developer developer = developerController.createDeveloper(firstName, lastName, listSkill, idSpec, status.toUpperCase());
        System.out.println("Developer created "+developer);


    }

    public void updateDeveloper() {
        System.out.println("Enter ID Developer for update");
        Integer ID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter FirstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter LastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter ID skill for developer");
        List <Integer> listSkill = new ArrayList<>();
        String command = "y";
        while(command.equals("y")) {
            Integer idSkill = scanner.nextInt();
            scanner.nextLine();
            listSkill.add(idSkill);
            System.out.println("Enter \"y\" for add skill or \"n\" for go next");
            command = scanner.nextLine();
        }

        System.out.println("Enter ID specialty for developer");
        Integer idSpec = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter status(ACTIVE or DELETED)");
        String status = scanner.nextLine();

        Developer developer = developerController.updateDeveloper(ID, firstName, lastName, listSkill, idSpec, status.toUpperCase());
        System.out.println("Developer update "+developer);
    }

    public void viewAllDevelopers() {
        System.out.println(developerController.getAllDevelopers());
    }

    public void viewByIdDeveloper() {
        System.out.println("Enter ID Developer for view");
        Integer ID = scanner.nextInt();
        System.out.println(developerController.getDeveloper(ID));
    }

    public void deleteByIdDeveloper() {
        System.out.println("Enter ID Developer for delete");
        Integer ID = scanner.nextInt();
        developerController.deleteDeveloper(ID);
    }
}