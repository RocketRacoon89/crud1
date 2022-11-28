package com.Mike.crud.repository.database;

import com.Mike.crud.model.Developer;
import com.Mike.crud.model.Skill;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbDevSkillsRepositoryImpl {

    PreparedStatement preparedStatement;

    public void saveDevSkills(Developer developer) {
        String sql = "INSERT INTO developer_skills(id_developer, id_skill) VALUES(?, ?)";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            preparedStatement.setInt(1, developer.getId());
            for(int i=0; i<developer.getSkills().size();i++){
                preparedStatement.setInt(2,developer.getSkills().get(i).getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Developers Skills inserted");
    }

    public void updateDevSkills(Developer developer) {
        deleteDevSkills(developer.getId());
        saveDevSkills(developer);
    }


    public void deleteDevSkills(Integer idDev) {
        String sql = "DELETE FROM developer_skills WHERE id_developer = ?";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            preparedStatement.setInt(1, idDev);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
