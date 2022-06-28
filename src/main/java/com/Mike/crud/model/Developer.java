package com.Mike.crud.model;

import java.util.List;

public class Developer {

    private Integer id;
    private String firstName;
    private String lastName;
    private List <Skill> skills;
    private Specialty specialty;
    private Status status = Status.ACTIVE;




    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public String getFirstName() {
        return firstName;

    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setSkills(List<Skill> skills) {
        this.skills=skills;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty=specialty;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public String toString() {
        return "ID: "+id+", "+lastName+" "+firstName+", spec: "+specialty+", skills: "+skills+". STATUS: "+status+".";
    }
}