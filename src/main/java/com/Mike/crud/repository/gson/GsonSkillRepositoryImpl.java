package com.Mike.crud.repository.gson;

import com.Mike.crud.model.Skill;
import com.Mike.crud.repository.SkillRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GsonSkillRepositoryImpl implements SkillRepository {

    private final Gson gson = new Gson();
    private final String skills_file_path = "src//main//resources//Skills.json";

    private List<Skill> getAllSkillsInternal() {
        //TODO: get list from file
        List<Skill> list = new ArrayList<>();
        try(FileReader reader = new FileReader(skills_file_path)) {
//			list.addAll(gson.fromJson(reader, ArrayList.class));
            List<Skill> supList = gson.fromJson(reader, new TypeToken<ArrayList<Skill>>(){}.getType());
            if(supList==null) {List<Skill> respList = new ArrayList<>(); return respList;}
            list.addAll(supList);
            return list;
        } catch(FileNotFoundException e) {
            System.out.println(e);
            return null;
        } catch(IOException e) {
            System.out.println(e);
            return null;
        }

    }

    private void writeSkillsToFile(List<Skill> skills) {
        //TODO: write skills to file
        try(FileWriter writer = new FileWriter(skills_file_path)) {
            gson.newBuilder().setPrettyPrinting().create();
            gson.toJson(skills, writer);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    private Integer generateId(List<Skill> skills) {
        if(skills.size() == 0) {return 1;}
        Skill skillWithMaxId = skills.stream().max(Comparator.comparing(Skill::getId)).orElse(null);
        return  skillWithMaxId.getId() + 1;
    }

    @Override
    public Skill getById(Integer id) {
        return getAllSkillsInternal().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Skill> getAll() {
        return getAllSkillsInternal();
    }

    @Override
    public Skill save(Skill skill) {
        List<Skill> currentSkills = getAllSkillsInternal();
        Integer newId = generateId(currentSkills);
        skill.setId(newId);
        currentSkills.add(skill);
        writeSkillsToFile(currentSkills);
        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        List<Skill> currentSkills = getAllSkillsInternal();
        currentSkills.forEach(s -> {
            if(s.getId().equals(skill.getId())) {
                s.setSkill(skill.getSkill());
                s.setStatus(skill.getStatus());
            }
        });
        writeSkillsToFile(currentSkills);
        return skill;
    }

    public void deleteById(Integer id) {
        List<Skill> currentSkills = getAllSkillsInternal();
        currentSkills.removeIf(s -> s.getId().equals(id));
        writeSkillsToFile(currentSkills);
    }
}
