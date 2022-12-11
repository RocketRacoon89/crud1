package com.Mike.crud.repository.database;

import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.SkillRepository;
import com.Mike.crud.utils.JdbcUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbSkillRepositoryImpl implements SkillRepository {

        @Override
    public Skill getById(Integer id) {
            PreparedStatement preparedStatement;
            String sql = "SELECT * FROM skills WHERE id = ?";
            Skill skill = new Skill();
            try {
                preparedStatement = JdbcUtils.getPreparedStatement(sql);
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
            PreparedStatement preparedStatement;
            List<Skill> list = new ArrayList<>();
            String sql = "SELECT * FROM skills";
            try {
                preparedStatement = JdbcUtils.getCon().prepareStatement(sql);
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
            PreparedStatement preparedStatement;
            String sql = "INSERT INTO skills(skill, status) VALUES(?, ?);";

            try {
                preparedStatement = JdbcUtils.getPreparedStatement(sql);
                preparedStatement.setString(1, skill.getSkill());
                preparedStatement.setString(2, skill.getStatus().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return skill;
        }

        @Override
    public Skill update(Skill skill) {
            PreparedStatement preparedStatement;
        String sql = "UPDATE skills SET skill = ?, status = ? WHERE id = ?";
        try {
            preparedStatement = JdbcUtils.getCon().prepareStatement(sql);
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
            PreparedStatement preparedStatement;
        String sql = "DELETE FROM skills WHERE id = ?";
        try {
            preparedStatement = JdbcUtils.getCon().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
