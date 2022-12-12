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

        String sql = "SELECT * FROM skills;";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);) {
//            preparedStatement.setInt(1,2);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
