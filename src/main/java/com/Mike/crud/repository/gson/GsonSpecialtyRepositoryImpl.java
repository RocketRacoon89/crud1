package com.Mike.crud.repository.gson;

import com.Mike.crud.model.Specialty;
import com.Mike.crud.repository.SpecialtyRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GsonSpecialtyRepositoryImpl implements SpecialtyRepository {

    private final Gson gson = new Gson();
    private final String skills_file_path = "src//main//resources//Specialties.json";

    private List<Specialty> getAllSpecialtyInternal() {
        List<Specialty> list = new ArrayList<>();
        try {
            FileReader reader = new FileReader(skills_file_path);
            List<Specialty> supList = gson.fromJson(reader, new TypeToken<ArrayList<Specialty>>(){}.getType());
            if(supList==null) {List<Specialty> respList = new ArrayList<>(); return respList;}
            list.addAll(supList);
            return list;
        } catch(FileNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }

    private void writeSpecialtyToFile(List<Specialty> specialties) {
        try(FileWriter writer = new FileWriter(skills_file_path);) {
            gson.toJson(specialties, writer);
        } catch(IOException e) {
            System.out.println(e);
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
    public Specialty save(Specialty t) {
        List<Specialty> currentSpecialties = getAllSpecialtyInternal();
        System.out.println(currentSpecialties);
        Integer newId = generateId(currentSpecialties);
        t.setId(newId);
        currentSpecialties.add(t);
        writeSpecialtyToFile(currentSpecialties);
        return t;
    }

    @Override
    public Specialty update(Specialty t) {
        List<Specialty> currentSpecialties = getAllSpecialtyInternal();
        currentSpecialties.forEach(s -> {
                    if(s.getId().equals(t.getId())) {
                        s.setId(t.getId());
                        s.setSpecialty(t.getSpecialty());
                        s.setStatus(t.getStatus());
                    }
                }
        );
        writeSpecialtyToFile(currentSpecialties);
        return t;
    }

    @Override
    public void deleteById(Integer id) {
        List<Specialty> currentSpecialties = getAllSpecialtyInternal();
        currentSpecialties.removeIf(s -> s.getId().equals(id));
        writeSpecialtyToFile(currentSpecialties);

    }
}
