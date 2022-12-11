package com.Mike.crud.repository.database;

import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.SpecialtyRepository;
import com.Mike.crud.utils.JdbcUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbSpecialtyRepositoryImpl implements SpecialtyRepository {

    @Override
    public Specialty getById(Integer id) {
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM specialties WHERE id = ?";
        Specialty retSpecialty = new Specialty();
        try {
            preparedStatement = JdbcUtils.getPreparedStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idSpec = resultSet.getInt("id");
                String spec = resultSet.getNString("specialty");
                String status = resultSet.getNString("status");
                retSpecialty.setId(idSpec);
                System.out.println(idSpec+" ID");
                retSpecialty.setSpecialty(spec);
                retSpecialty.setStatus(Status.valueOf(status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retSpecialty;
    }

    @Override
    public List<Specialty> getAll() {
        PreparedStatement preparedStatement;
        List<Specialty> list = new ArrayList<>();
        String sql = "SELECT * FROM specialties";
        try {
            preparedStatement = JdbcUtils.getCon().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Specialty specialty = new Specialty();
                int id = resultSet.getInt("id");
                String name = resultSet.getNString("specialty");
                Status status = Status.valueOf(resultSet.getNString("status"));
                specialty.setId(id);
                specialty.setSpecialty(name);
                specialty.setStatus(status);
                list.add(specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Specialty save(Specialty specialty) {
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO specialties(specialty, status) VALUES(?, ?);";

        try {
            preparedStatement = JdbcUtils.getPreparedStatement(sql);
            preparedStatement.setString(1, specialty.getSpecialty());
            preparedStatement.setString(2, specialty.getStatus().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialty;

    }

    @Override
    public Specialty update(Specialty specialty) {
        PreparedStatement preparedStatement;
        String sql = "UPDATE specialties SET specialty = ?, status = ? WHERE id = ?";
        try {
            preparedStatement = JdbcUtils.getCon().prepareStatement(sql);
            preparedStatement.setString(1, specialty.getSpecialty());
            preparedStatement.setString(2, specialty.getStatus().toString());
            preparedStatement.setInt(3, specialty.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM specialties WHERE id = ?";
        try {
            preparedStatement = JdbcUtils.getCon().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
