package com.Mike.crud.repository.gson;

import com.Mike.crud.model.Developer;
import com.Mike.crud.repository.DeveloperRepository;
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

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {

    private final Gson gson = new Gson();
    private final String skills_file_path = "src//main//resources//Developers.json";

    private List<Developer> getAllDeveloperInternal() {
        List<Developer> list = new ArrayList<>();
        try {
            FileReader reader = new FileReader(skills_file_path);
            List<Developer> supList = gson.fromJson(reader, new TypeToken<ArrayList<Developer>>(){}.getType());
            if(supList==null) {List<Developer> respList = new ArrayList<>(); return respList;}
            list.addAll(supList);
            return list;
        } catch(FileNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }

    private void writeDeveloperToFile(List <Developer> developers) {
        try(FileWriter writer = new FileWriter(skills_file_path);) {
            gson.toJson(developers, writer);
        } catch(IOException e) {
            System.out.println(e);
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
    public Developer save(Developer t) {
        List<Developer> currentDeveloper = getAllDeveloperInternal();
        Integer newId = generateId(currentDeveloper);
        t.setId(newId);
        currentDeveloper.add(t);
        writeDeveloperToFile(currentDeveloper);
        return t;
    }

    @Override
    public Developer update(Developer t) {
        List<Developer> currentDeveloper = getAllDeveloperInternal();
        currentDeveloper.forEach(s -> {
            if(s.getId().equals(t.getId())) {
                s.setId(t.getId());
                s.setFirstName(t.getFirstName());
                s.setLastName(t.getLastName());
                s.setSkills(t.getSkills());
                s.setSpecialty(t.getSpecialty());
                s.setStatus(t.getStatus());
            }
        });
        writeDeveloperToFile(currentDeveloper);
        return t;
    }

    @Override
    public void deleteById(Integer id) {
        List<Developer> currentDeveloper = getAllDeveloperInternal();
        currentDeveloper.removeIf(s -> s.getId().equals(id));
        writeDeveloperToFile(currentDeveloper);

    }

}