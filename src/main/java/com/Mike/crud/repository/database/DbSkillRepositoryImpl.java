package com.Mike.crud.repository.database;

import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.SkillRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DbSkillRepositoryImpl implements SkillRepository {

    PreparedStatement preparedStatement;


    private List<Skill> getAllSkillsInternal() {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT * FROM skills";
        try {
            preparedStatement = DBconnect2.getCon().prepareStatement(sql);
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

    private Integer generateId(List<Skill> skills) {
        if(skills.size()==0) {return 1;}
        Skill skillWithMaxId = skills.stream().max(Comparator.comparing(Skill::getId)).orElse(null);
        return  skillWithMaxId.getId() + 1;
    }

    private void writeSkillsToDb(Skill skill) {
        String sql = "INSERT INTO skills(id, skill, status) VALUES (?,?,?)";
        try {
            preparedStatement = DBconnect2.getCon().prepareStatement(sql);
            preparedStatement.setInt(1, skill.getId());
            preparedStatement.setString(2,skill.getSkill());
            preparedStatement.setString(3,skill.getStatus().toString());
            System.out.println("Insert row: "+preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        @Override
    public Skill getById(Integer id) {
        return getAllSkillsInternal().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

        @Override
    public List<Skill> getAll()  {
        return getAllSkillsInternal();
    }

        @Override
    public Skill save(Skill skill)  {
        List<Skill> currentSkills = getAllSkillsInternal();
        skill.setId(generateId(currentSkills));
        writeSkillsToDb(skill);
        return skill;
    }

        @Override
    public Skill update(Skill skill) {
        String sql = "UPDATE skills SET skill = ?, status = ? WHERE id = ?";
        try {
            preparedStatement = DBconnect2.getCon().prepareStatement(sql);
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
        try {
            preparedStatement = DBconnect2.getCon().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
