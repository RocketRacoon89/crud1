import com.Mike.crud.model.Developer;
import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.database.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Test1 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        DbSkillRepositoryImpl dbSkillRepository = new DbSkillRepositoryImpl();
//        dbSkillRepository.deleteById(2);

//        Skill newSkill = new Skill();
//        newSkill.setSkill("React");
//        newSkill.setStatus(Status.ACTIVE);
//        dbSkillRepository.save(newSkill);

//        DbSpecialtyRepositoryImpl dbSpecialtyRepository = new DbSpecialtyRepositoryImpl();
//        Specialty spec = new Specialty();
//        spec.setId(777);
//        spec.setSpecialty("C++");
//        spec.setStatus(Status.ACTIVE);
//        dbSpecialtyRepository.save(spec);

//        DbDeveloperRepositoryImpl dbDeveloperRepository = new DbDeveloperRepositoryImpl();
//        Developer dev = new Developer();
//        dev.setId(5);
//        dev.setFirstName("Dmitriy");
//        dev.setLastName("Ivanov");
//        dev.setSpecialty(spec);
//        dev.setSkills(dbSkillRepository.getAll());
//        dev.setStatus(Status.ACTIVE);
//        new DbDevSkillsRepositoryImpl().saveDevSkills(dev);

        Class.forName("com.mysql.cj.jdbc.Driver");
//
//        System.out.println(dbSkillRepository.getById(1));


    }
}
