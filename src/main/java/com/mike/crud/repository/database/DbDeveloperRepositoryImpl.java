package com.mike.crud.repository.database;

import com.mike.crud.model.Developer;
import com.mike.crud.model.Skill;
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
        List<Skill> devSkills = new ArrayList<>();
        String sqlDev = "SELECT * FROM developers WHERE id = ?;";
        String sqlSkills = "SELECT id_skill FROM developer_skills WHERE id_developer = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sqlDev);
             PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sqlSkills);) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                developer.setId(resultSet.getInt("id"));
                developer.setFirstName(resultSet.getNString("FirstName"));
                developer.setLastName(resultSet.getNString("LastName"));
                developer.setSpecialty(new DbSpecialtyRepositoryImpl().getById(resultSet.getInt("id_specialty")));
                developer.setStatus(Status.valueOf(resultSet.getNString("status")));


                preparedStatement1.setInt(1,developer.getId());
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    devSkills.add(new DbSkillRepositoryImpl().getById(resultSet1.getInt("id_skill")));
                }
                developer.setSkills(devSkills);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }


    @Override
    public Developer save(Developer developer) {
        String sql_dev = "INSERT INTO developers(firstname, lastname, status, id_specialty) VALUES(?, ?, ?, ?);";
        String sql_spec = "INSERT INTO developer_skills(id_developer, id_skill) VALUES(?, ?);";
        String sql_getId = "SELECT id FROM developers WHERE firstname = ? and lastname = ? and id_specialty = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql_dev);
             PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sql_getId);
             PreparedStatement preparedStatement2 = JdbcUtils.getPreparedStatement(sql_spec);) {

            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3, developer.getStatus().toString());
            preparedStatement.setInt(4, developer.getSpecialty().getId());
            if(preparedStatement.executeUpdate()==1) {

                preparedStatement1.setString(1, developer.getFirstName());
                preparedStatement1.setString(2, developer.getLastName());
                preparedStatement1.setInt(3, developer.getSpecialty().getId());
                ResultSet resultSet = preparedStatement1.executeQuery();
                while (resultSet.next()) {
                    developer.setId(resultSet.getInt("id"));
                    System.out.println("save return id "+ developer.getId());
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