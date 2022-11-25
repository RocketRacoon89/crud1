package com.Mike.crud.repository.database;

import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.SkillRepository;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbSkillRepositoryImpl implements SkillRepository {

    PreparedStatement preparedStatement;

    private List<Skill> getAllSkillIntDb() {
        return null;
    }

    private List<Skill> getAllSkillsInternal() throws SQLException {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT * FROM skills";
        preparedStatement = DBconnect.getCon().prepareStatement(sql);
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
        return list;
    }


    @Override
    public Skill getById(Integer id) throws SQLException {
        return getAllSkillsInternal().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        }

    @Override
    public List<Skill> getAll() {
        return null;
    }

    @Override
    public Skill save(Skill skill) {
        return null;
    }

    @Override
    public Skill update(Skill skill) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
