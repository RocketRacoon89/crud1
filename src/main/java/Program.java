import com.mike.crud.view.DeveloperView;
import com.mike.crud.view.SkillView;
import com.mike.crud.view.SpecialtyView;

import java.util.Scanner;

public class Program {

    static private final Scanner scanner = new Scanner(System.in);
    static private int choice;

    public static void main(String[] args) {
//        String [] path = {"src//main//resources//Specialties.json",
//                "src//main//resources//Developers.json",
//                "src//main//resources//Skills.json"};
//        File file;
//        for(int i=0; i<path.length; i++) {
//            file = new File(path[i]);
//            if (!file.exists()) {
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    System.out.println(e);
//                }
//            }
//        }
        System.out.println("Enter please next move: ");
        System.out.println("Enter \"1\" for work with Developer");
        System.out.println("Enter \"2\" for work with Skill");
        System.out.println("Enter \"3\" for work with Specialty");
        System.out.println("Enter \"4\" Exit");
        choice = scanner.nextInt();
        if(choice==1) workDeveloper();
        if(choice==2) workSkill();
        if(choice==3) workSpecialty();
        if(choice==4) System.exit(0);
    }

    static public void workDeveloper() {
        System.out.println("Enter please next move: ");
        System.out.println("Enter \"1\" for create new Developer");
        System.out.println("Enter \"2\" for update Developer");
        System.out.println("Enter \"3\" for get All Developers");
        System.out.println("Enter \"4\" for get Developer by ID");
        System.out.println("Enter \"5\" for delete Developer by ID");
        System.out.println("Enter \"6\" for back");
        choice = scanner.nextInt();
        DeveloperView view = new DeveloperView();

        if(choice==1) view.createDeveloper();

        if(choice==2) view.updateDeveloper();

        if(choice==3) view.viewAllDevelopers();

        if(choice==4) view.viewByIdDeveloper();

        if(choice==5) view.deleteByIdDeveloper();

        if(choice==6) main(null);

        main(null);
    }

    static public void workSkill() {
        System.out.println("Enter please next move: ");
        System.out.println("Enter \"1\" for create new Skill");
        System.out.println("Enter \"2\" for update Skill");
        System.out.println("Enter \"3\" for get All Skills");
        System.out.println("Enter \"4\" for get Skill by ID");
        System.out.println("Enter \"5\" for delete Skill by ID");
        System.out.println("Enter \"6\" for back");
        choice = scanner.nextInt();
        SkillView view = new SkillView();

        if(choice==1) view.createSkill();

        if(choice==2) view.updateSkill();

        if(choice==3) view.viewSkills();

        if(choice==4) view.viewSkill();

        if(choice==5) view.deleteSkill();

        if(choice==6) main(null);

        main(null);
    }

    static public void workSpecialty() {
        System.out.println("Enter please next move: ");
        System.out.println("Enter \"1\" for create new Specialty");
        System.out.println("Enter \"2\" for update Specialty");
        System.out.println("Enter \"3\" for get All Specialties");
        System.out.println("Enter \"4\" for get Specialty by ID");
        System.out.println("Enter \"5\" for delete Specialty by ID");
        System.out.println("Enter \"6\" for back");
        choice = scanner.nextInt();
        SpecialtyView view = new SpecialtyView();

        if(choice==1) view.createSpecialty();

        if(choice==2) view.updateSpecialty();

        if(choice==3) view.viewSpecialties();

        if(choice==4) view.viewSpecialty();

        if(choice==5) view.deleteSpecialty();

        if(choice==6) main(null);

        main(null);
    }
}
