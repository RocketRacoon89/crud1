package com.mike.crud.repository.database;

import com.mike.crud.model.Skill;
import com.mike.crud.model.Status;
import com.mike.crud.repository.SkillRepository;
import com.mike.crud.utils.JdbcUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbSkillRepositoryImpl implements SkillRepository {

    @Override
    public Skill getById(Integer id) {
            String sql = "SELECT * FROM skills WHERE id = ?";
            Skill skill = new Skill();

            try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql);) {

                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int idSpec = resultSet.getInt("id");
                    String spec = resultSet.getNString("skill");
                    String status = resultSet.getNString("status");
                    skill.setId(idSpec);
                    System.out.println(idSpec+" ID");
                    skill.setSkill(spec);
                    skill.setStatus(Status.valueOf(status));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return skill;
    }

        @Override
    public List<Skill> getAll()  {

            List<Skill> list = new ArrayList<>();
            String sql = "SELECT * FROM skills";

            try (PreparedStatement preparedStatement = JdbcUtils.getCon().prepareStatement(sql);) {

                ResultSet resultSet = preparedStatement.executeQuery(sql);
                while (resultSet.next()) {
                    Skill skill = new Skill();
                    int id = resultSet.getInt("id");
                    String name = resultSet.getNString("skill");
                    Status status = Status.valueOf(resultSet.getNString("status"));
                    skill.setId(id);
                    skill.setSkill(name);
                    skill.setStatus(status);
                    list.add(skill);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
    }

        @Override
    public Skill save(Skill skill)  {
            String sql = "INSERT INTO skills(skill, status) VALUES(?, ?);";
            int idSkill = 0;

            try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithGeneratedKeys(sql);) {

                preparedStatement.setString(1, skill.getSkill());
                preparedStatement.setString(2, skill.getStatus().toString());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    idSkill = resultSet.getInt(1);
                    skill.setId(idSkill);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return skill;
        }

        @Override
    public Skill update(Skill skill) {
        String sql = "UPDATE skills SET skill = ?, status = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = JdbcUtils.getCon().prepareStatement(sql);) {

            preparedStatement.setString(1, skill.getSkill());
            preparedStatement.setString(2, skill.getStatus().toString());
            preparedStatement.setInt(3, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM skills WHERE id = ?";

        try (PreparedStatement preparedStatement = JdbcUtils.getCon().prepareStatement(sql);) {

            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
