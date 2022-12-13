import com.Mike.crud.model.Developer;
import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.database.*;
import com.Mike.crud.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args)  {
//        throws SQLException, ClassNotFoundException
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
//        PreparedStatement preparedStatement;
//        String sql = "insert into specialties(id, specialty, status) values(?, ?, ?);";
//        preparedStatement = JdbcUtils.getPreparedStatement(sql);
//        preparedStatement.setInt(1, 61);
//        preparedStatement.setString(2,"test3");
//        preparedStatement.setString(3, "test4");
//        preparedStatement.executeUpdate();
//
//
////
////        System.out.println(dbSkillRepository.getById(1));
//        String sql1 = "select * from specialties;";
//        ResultSet resultSet = JdbcUtils.getPreparedStatement(sql1).executeQuery();
//        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String spec = resultSet.getNString("specialty");
//            String status = resultSet.getNString("status");
//            System.out.println(id+", "+spec+", "+status);
//        }

//        List<String> list = new ArrayList<>();
//        list.add("yyyyyyy");
//        list.add("ssadsah");
//        list.add("ommkdf");
//        list.add("pdsgnf");
//        list.add("nsdflds");
//        list.add("[[[op]]]");
//        list.add("jnkfs");
//        list.add("dfsgkff");
//        list.add("foiml");
//
//        String sql = "INSERT INTO specialties(id, specialty) VALUES(?, ?);";
//        PreparedStatement preparedStatement;
//        preparedStatement = JdbcUtils.getPreparedStatement(sql);
//        int x = 1;
//        for(String s : list) {
//            x = x+3;
//            preparedStatement.setInt(1,x);
//            preparedStatement.setString(2,s);
//            preparedStatement.executeUpdate();
//        }


//DbDeveloperRepositoryImpl dbDeveloperRepository = new DbDeveloperRepositoryImpl();
//        System.out.println(dbDeveloperRepository.getAll());
//       System.out.println(dbDeveloperRepository.getById(6));
        List<Developer> developers = new ArrayList<>();

        String sql = "SELECT d.id, d.FirstName, d.LastName, d.Status, d.id_specialty, ds.id_developer, ds.id_skill, s.id, s.skill, s.status  FROM developers d" +
                " JOIN developer_skills ds ON ds.id_developer=d.id"+
                " JOIN skills s ON s.id=ds.id_skill;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);) {
            while (resultSet.next()) {

                Developer developer = new Developer();
                Skill skill = new Skill();
                List<Skill> skills = new ArrayList<>();

                developer.setId(resultSet.getInt("d.id"));
                developer.setFirstName(resultSet.getString("d.firstname"));
                developer.setLastName(resultSet.getString("d.lastname"));
                developer.setStatus(Status.valueOf(resultSet.getString("d.status")));
                developer.setSpecialty(new DbSpecialtyRepositoryImpl().getById(resultSet.getInt("d.id_specialty")));

                if(!developers.contains(developer)) {
                    skill.setId(resultSet.getInt("s.id"));
                    skill.setSkill(resultSet.getString("s.skill"));
                    skill.setStatus(Status.valueOf(resultSet.getString("s.status")));
                    skills.add(skill);
                    developer.setSkills(skills);
                    developers.add(developer);
                } else {
                    skills = developers.get(developers.indexOf(developer)).getSkills();
                    skills.add(new DbSkillRepositoryImpl().getById(resultSet.getInt("ds.id_skill")));
                    developers.get(developers.indexOf(developer)).setSkills(skills);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(developers);

    }
}
