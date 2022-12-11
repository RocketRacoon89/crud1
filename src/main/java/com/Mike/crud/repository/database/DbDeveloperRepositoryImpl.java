package com.Mike.crud.repository.database;

import com.Mike.crud.model.Developer;
import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.DeveloperRepository;
import com.Mike.crud.utils.JdbcUtils;

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
        //TODO: use 1 request with JOIN
        String sql = "SELECT * FROM developers;";
        try {
            PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Developer developer = mapResultSetToDeveloper(resultSet);
                sql = "SELECT * FROM developer_skills WHERE id_developer = ?;";
                PreparedStatement preparedStatementEn = JdbcUtils.getPreparedStatement(sql);
                preparedStatementEn.setInt(1, developer.getId());
                ResultSet resultSet1 = preparedStatementEn.executeQuery();
                List<Skill> skills = new ArrayList<>();
                while(resultSet1.next()) {
                    Skill skill = new DbSkillRepositoryImpl().getById(resultSet1.getInt("id_skill"));
                    skills.add(skill);
                }
                developer.setSkills(skills);
                developers.add(developer);
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
        try {
            PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sqlDev);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //TODO: get developer
                developer.setId(resultSet.getInt("id"));
                developer.setFirstName(resultSet.getNString("FirstName"));
                developer.setLastName(resultSet.getNString("LastName"));
                developer.setSpecialty(new DbSpecialtyRepositoryImpl().getById(resultSet.getInt("id_specialty")));
                developer.setStatus(Status.valueOf(resultSet.getNString("status")));

                //TODO: get skill for developer
                preparedStatement = JdbcUtils.getPreparedStatement(sqlSkills);
                preparedStatement.setInt(1,developer.getId());
                ResultSet resultSet1 = preparedStatement.executeQuery();
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
        PreparedStatement preparedStatement;
        String sql_dev = "INSERT INTO developers(firstname, lastname, status, id_specialty) VALUES(?, ?, ?, ?);";
        String sql_spec = "INSERT INTO developer_skills(id_developer, id_skill) VALUES(?, ?);";
        String sql_getId = "SELECT id FROM developers WHERE firstname = ? and lastname = ? and id_specialty = ?;";

        try {
            preparedStatement = JdbcUtils.getPreparedStatement(sql_dev);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3, developer.getStatus().toString());
            preparedStatement.setInt(4, developer.getSpecialty().getId());
            if(preparedStatement.executeUpdate()==1) {

                //TODO: get developer_id for new developer
                PreparedStatement preparedStatement1 = JdbcUtils.getPreparedStatement(sql_getId);
                preparedStatement1.setString(1, developer.getFirstName());
                preparedStatement1.setString(2, developer.getLastName());
                preparedStatement1.setInt(3, developer.getSpecialty().getId());
                ResultSet resultSet = preparedStatement1.executeQuery();
                while (resultSet.next()) {
                    developer.setId(resultSet.getInt("id"));
                    System.out.println("save return id "+ developer.getId());
                }

                preparedStatement = JdbcUtils.getPreparedStatement(sql_spec);
                preparedStatement.setInt(1, developer.getId());
                for(Skill skill : developer.getSkills()) {
                    preparedStatement.setInt(2, skill.getId());
                    preparedStatement.executeUpdate();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        PreparedStatement preparedStatement;
        String sql = "UPDATE developers SET firstname = ?, lastname = ?, status = ?, id_specialty = ? WHERE id = ?;";

        try {
            preparedStatement = JdbcUtils.getPreparedStatement(sql);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3,developer.getStatus().toString());
            preparedStatement.setInt(4,developer.getSpecialty().getId());
            preparedStatement.setInt(5,developer.getId());
            if(preparedStatement.executeUpdate()==1) {
                sql = "SELECT id_skill from developer_skills WHERE id_developer = ?;";
                preparedStatement = JdbcUtils.getPreparedStatement(sql);
                preparedStatement.setInt(1,developer.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Skill> currentSkills = new ArrayList<>();
                List<Skill> newSkills = developer.getSkills();
                while (resultSet.next()) {
                    currentSkills.add(new DbSkillRepositoryImpl().getById(resultSet.getInt("id_skill")));
                }

                String sqlDelete = "DELETE FROM developer_skills WHERE id_developer = ? AND id_skill = ?;";
                for(Skill skill : currentSkills) {
                    if(!newSkills.contains(skill)) {
                        preparedStatement = JdbcUtils.getPreparedStatement(sqlDelete);
                        preparedStatement.setInt(1, developer.getId());
                        preparedStatement.setInt(2,skill.getId());
                        preparedStatement.executeUpdate();
                    } else {
                        newSkills.remove(skill);
                    }
                }

                String sqlInsert = "INSERT INTO developer_skills(id_developer, id_skill) VALUES(?, ?);";
                preparedStatement = JdbcUtils.getPreparedStatement(sqlInsert);
                preparedStatement.setInt(1, developer.getId());
                for(Skill skill : newSkills) {
                    preparedStatement.setInt(2, skill.getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement;
        String sql_dev = "DELETE FROM developers WHERE id = ?";
        String sql_skills = "DELETE FROM developer_skills WHERE id_developer = ?;";
        try {
            preparedStatement = JdbcUtils.getPreparedStatement(sql_dev);
            preparedStatement.setInt(1, id);
            if(preparedStatement.executeUpdate()==1) {
                    preparedStatement = JdbcUtils.getPreparedStatement(sql_skills);
                    preparedStatement.setInt(1,id);
                    preparedStatement.executeUpdate();
            }
            else throw new RuntimeException("Error when deleting.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Row Developer deleted");
    }
}