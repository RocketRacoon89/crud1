package com.Mike.crud.repository.database;

import com.Mike.crud.model.Developer;
import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Specialty;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.DeveloperRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DbDeveloperRepositoryImpl implements DeveloperRepository {

    PreparedStatement preparedStatement;

    private List<Skill> getSkillDeveloper(Integer idDev) {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT id_skill FROM developer_skills WHERE id_developer = ?";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                int idSkill = resultSet.getInt("id_skill");
                skills.add(new DbSkillRepositoryImpl().getById(idSkill));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    private List<Developer> getAllDeveloperInternal() {
        List<Developer> developers = new ArrayList<>();
        String sql = "SELECT * FROM developers";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fName = resultSet.getNString("FirstName");
                String lName = resultSet.getNString("LastName");
                Status status = Status.valueOf(resultSet.getNString("Status"));
                int idSpec = resultSet.getInt("id_specialty");
                Specialty specialty = (new DbSpecialtyRepositoryImpl().getById(idSpec));
                Developer developer = new Developer();
                developer.setId(id);
                developer.setFirstName(fName);
                developer.setLastName(lName);
                developer.setStatus(status);
                developer.setSpecialty(specialty);
                developer.setSkills(getSkillDeveloper(id));
                developers.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    private void writeDeveloperToDb(Developer developer) {
        String sql = "INSERT INTO developers(id, FirstName, LastName, Status, id_specialty) VALUES (?,?,?,?,?)";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            preparedStatement.setInt(1, developer.getId());
            preparedStatement.setString(2, developer.getFirstName());
            preparedStatement.setString(3, developer.getLastName());
            preparedStatement.setString(4, developer.getStatus().toString());
            preparedStatement.setInt(5, developer.getSpecialty().getId());
            new DbDevSkillsRepositoryImpl().saveDevSkills(developer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Integer generateId(List<Developer> developers) {
        if(developers.size()==0) {return 1;}
        Developer developerWithMaxId = developers.stream().max(Comparator.comparing(Developer::getId)).get();
        return developerWithMaxId.getId()+1;
    }

    @Override
    public Developer getById(Integer id) {
        return getAllDeveloperInternal().stream().filter(s->s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Developer> getAll() {
        return getAllDeveloperInternal();
    }

    @Override
    public Developer save(Developer developer) {
        developer.setId(generateId(getAllDeveloperInternal()));
        writeDeveloperToDb(developer);
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        String sql = "UPDATE developers" +
                "SET FirstName = ?," +
                "LastName = ?," +
                "Status = ?," +
                "id_specialty = ?" +
                "WHERE id = ?";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3,developer.getStatus().toString());
            preparedStatement.setInt(4,developer.getSpecialty().getId());
            preparedStatement.setInt(5,developer.getId());
            System.out.println("Developer update "+preparedStatement.executeUpdate(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM developers WHERE id = ?";
        try {
            preparedStatement = DBconnect.getCon().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Row Developer deleted");
    }
}
