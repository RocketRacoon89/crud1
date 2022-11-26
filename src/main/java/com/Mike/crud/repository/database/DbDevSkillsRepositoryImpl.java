package com.Mike.crud.repository.database;

import com.Mike.crud.model.Developer;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DbDevSkillsRepositoryImpl {

    PreparedStatement preparedStatement;

    public void saveDevSkills(Developer developer) {
        String sql = "INSERT INTO developer_skills(id_skill, id_developer) VALUES(?,?)";
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

    }
}
