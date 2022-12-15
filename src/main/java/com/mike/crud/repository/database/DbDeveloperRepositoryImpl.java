package com.mike.crud.repository.database;

import com.mike.crud.model.Developer;
import com.mike.crud.model.Skill;
import com.mike.crud.model.Specialty;
import com.mike.crud.model.Status;
import com.mike.crud.repository.DeveloperRepository;
import com.mike.crud.utils.JdbcUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbDeveloperRepositoryImpl implements DeveloperRepository {

    private Developer mapResultSetToDeveloper(ResultSet resultSet) throws SQLException {
        Developer developer = new Developer();
        int id = resultSet.getInt("id");
        String fName = resultSet.getNString("FirstName");
        String lName = resultSet.getNString("LastName");
        Status status = Status.valueOf(resultSet.getNString("Status"));
        developer.setId(id);
        developer.setFirstName(fName);
        developer.setLastName(lName);
        developer.setStatus(status);
        developer.setSpecialty(new DbSpecialtyRepositoryImpl().getById(resultSet.getInt("id_specialty")));
        return developer;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> developers = new ArrayList<>();
        int i = 0;

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
                    skill.setId(resultSet.getInt("s.id"));
                    skill.setSkill(resultSet.getString("s.skill"));
                    skill.setStatus(Status.valueOf(resultSet.getString("s.status")));
                    skills.add(skill);
                    developers.get(developers.indexOf(developer)).setSkills(skills);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }


    @Override
    public Developer getById(Integer id) {
        Developer developer = new Developer();
        Skill skill = new Skill();
        List<Skill> devSkills = new ArrayList<>();
        Specialty specialty = new Specialty();
        String sql = "SELECT * FROM developers d" +
                " JOIN developer_skills ds ON ds.id_developer=d.id" +
                " JOIN specialties s ON s.id=d.id_specialty" +
                " JOIN skills sk ON sk.id=ds.id_skill" +
                " WHERE d.id = ?;";

        try (PreparedStatement preparedStatement2 = JdbcUtils.getPreparedStatement(sql)) {

            preparedStatement2.setInt(1, id);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                if(developer.getId()==null) {
                    developer.setId(resultSet2.getInt("id"));
                    developer.setFirstName(resultSet2.getNString("FirstName"));
                    developer.setLastName(resultSet2.getNString("LastName"));
                    specialty.setId(resultSet2.getInt("s.id"));
                    specialty.setSpecialty(resultSet2.getString("s.specialty"));
                    specialty.setStatus(Status.valueOf(resultSet2.getString("s.status")));
                    developer.setSpecialty(specialty);
                    skill.setId(resultSet2.getInt("sk.id"));
                    skill.setSkill(resultSet2.getString("sk.skill"));
                    skill.setStatus(Status.valueOf(resultSet2.getString("sk.status")));
                    devSkills.add(skill);
                    developer.setSkills(devSkills);
                } else {
                    skill.setId(resultSet2.getInt("sk.id"));
                    skill.setSkill(resultSet2.getString("sk.skill"));
                    skill.setStatus(Status.valueOf(resultSet2.getString("sk.status")));
                    devSkills.add(skill);
                    developer.setSkills(devSkills);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    //TODO: use DB internal auto increment mechanism in the DB and get created ID from DB using RETURN_GENERATED_KEYS
    //TODO: insert data to related tables inside this method - private method is allowed
    @Override
    public Developer save(Developer developer) {
        String sql_dev = "INSERT INTO developers(firstname, lastname, status, id_specialty) VALUES(?, ?, ?, ?);";
        String sql_spec = "INSERT INTO developer_skills(id_developer, id_skill) VALUES(?, ?);";
        int idDev = 0;

        try (PreparedStatement preparedStatement2 = JdbcUtils.getPreparedStatement(sql_spec);
             PreparedStatement preparedStatement3 = JdbcUtils.getPreparedStatementWithGeneratedKeys(sql_dev)) {

            preparedStatement3.setString(1, developer.getFirstName());
            preparedStatement3.setString(2, developer.getLastName());
            preparedStatement3.setString(3, developer.getStatus().toString());
            preparedStatement3.setInt(4, developer.getSpecialty().getId());
            if(preparedStatement3.executeUpdate()==1) {
                ResultSet resultSet = preparedStatement3.getGeneratedKeys();
                while (resultSet.next()) {
                    idDev = resultSet.getInt(1);
                    developer.setId(idDev);
                }

                preparedStatement2.setInt(1, developer.getId());
                for(Skill skill : developer.getSkills()) {
                    preparedStatement2.setInt(2, skill.getId());
                    preparedStatement2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    
    @Override
    public Developer update(Developer developer) {
        String sql = "UPDATE developers SET firstname = ?, lastname = ?, status = ?, id_specialty = ? WHERE id = ?;";
        String sqlCS = "SELECT id_skill from developer_skills WHERE id_developer = ?;";
        String sqlDelete = "DELETE FROM developer_skills WHERE id_developer = ? AND id_skill = ?;";
        String sqlInsert = "INSERT INTO developer_skills(id_developer, id_skill) VALUES(?, ?);";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql);
             PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sqlCS);
             PreparedStatement preparedStatement2 = JdbcUtils.getPreparedStatement(sqlDelete);
             PreparedStatement preparedStatement4 = JdbcUtils.getPreparedStatement(sqlInsert);) {

            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3,developer.getStatus().toString());
            preparedStatement.setInt(4,developer.getSpecialty().getId());
            preparedStatement.setInt(5,developer.getId());
            if(preparedStatement.executeUpdate()==1) {


                preparedStatement1.setInt(1,developer.getId());
                ResultSet resultSet = preparedStatement1.executeQuery();
                List<Skill> currentSkills = new ArrayList<>();
                List<Skill> newSkills = developer.getSkills();
                while (resultSet.next()) {
                    currentSkills.add(new DbSkillRepositoryImpl().getById(resultSet.getInt("id_skill")));
                }
                for(Skill skill : currentSkills) {
                    if(!newSkills.contains(skill)) {
                        preparedStatement2.setInt(1, developer.getId());
                        preparedStatement2.setInt(2,skill.getId());
                        preparedStatement2.executeUpdate();
                    } else {
                        newSkills.remove(skill);
                    }
                }
                preparedStatement4.setInt(1, developer.getId());
                for(Skill skill : newSkills) {
                    preparedStatement4.setInt(2, skill.getId());
                    preparedStatement4.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public void deleteById(Integer id) {
        String sql_dev = "DELETE FROM developers WHERE id = ?";
        String sql_skills = "DELETE FROM developer_skills WHERE id_developer = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql_dev);
             PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sql_skills);) {

            preparedStatement.setInt(1, id);
            if(preparedStatement.executeUpdate()==1) {
                    preparedStatement1.setInt(1,id);
                    preparedStatement1.executeUpdate();
            }
            else throw new RuntimeException("Error when deleting.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Row Developer deleted");
    }
}