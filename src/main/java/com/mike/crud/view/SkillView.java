package com.mike.crud.view;

import com.mike.crud.controller.SkillController;
import com.mike.crud.model.Skill;

import java.util.Scanner;

public class SkillView {

    private final Scanner scanner = new Scanner(System.in);
    private final SkillController skillController = new SkillController();

    public void createSkill() {
        System.out.println("Enter name skill:");
        String name = scanner.nextLine();
        System.out.println("Enter skill status:");
        String status = scanner.nextLine();

        Skill skill = skillController.createSkill(name, status.toUpperCase());
        System.out.println("Created skill "+skill);
    }

    public void updateSkill() {
        System.out.println("Enter ID skill:");
        Integer ID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter name skill:");
        String name = scanner.nextLine();
        System.out.println("Enter skill status:");
        String status = scanner.nextLine();

        Skill skill = skillController.updateSkill(ID, name, status.toUpperCase());
        System.out.println("Updated skill "+skill);
    }

    public void deleteSkill() {
        System.out.println("Enter ID skill for delete:");
        Integer ID = scanner.nextInt();
        skillController.deleteSkill(ID);
        System.out.println("Skill ID: "+ID+" Deleted");
    }

    public void viewSkills() {
        System.out.println(skillController.getAllSkills());
    }

    public void viewSkill() {
        System.out.println("Enter ID skill:");
        Integer ID = scanner.nextInt();
        System.out.println(skillController.getSkill(ID));
    }

}
