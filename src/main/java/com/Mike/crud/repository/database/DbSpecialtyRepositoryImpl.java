package com.Mike.crud.repository.database;

import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.SpecialtyRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DbSpecialtyRepositoryImpl implements SpecialtyRepository {

    PreparedStatement preparedStatement;

    private List<Specialty> getAllSpecialtyInternal() {
        List<Specialty> list = new ArrayList<>();
        String sql = "SELECT * FROM specialties";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
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

    private void writeSpecialtyToDb(Specialty specialty) {
        String sql = "INSERT INTO specialties(id, specialty, status) VALUES (?,?,?)";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            preparedStatement.setInt(1,specialty.getId());
            preparedStatement.setString(2, specialty.getSpecialty());
            preparedStatement.setString(3,specialty.getStatus().toString());
            System.out.println("Insert row: "+preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Integer generateId(List<Specialty> specialties) {
        if(specialties.size()==0) {return 1;}
        Specialty specialtyWithMaxId = specialties.stream().max(Comparator.comparing(Specialty::getId)).get();
        return specialtyWithMaxId.getId() +1;
    }

    @Override
    public Specialty getById(Integer id) {
        return getAllSpecialtyInternal().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Specialty> getAll() {
        return getAllSpecialtyInternal();
    }

    @Override
    public Specialty save(Specialty specialty) {
//        List<Specialty> list = getAllSpecialtyInternal();
        specialty.setId(generateId(getAllSpecialtyInternal()));
        writeSpecialtyToDb(specialty);
        return specialty;
    }

    @Override
    public Specialty update(Specialty specialty) {
        String sql = "UPDATE specialties SET skill = ?, status = ? WHERE id = ?";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
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
        String sql = "DELETE FROM specialties WHERE id = ?";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
