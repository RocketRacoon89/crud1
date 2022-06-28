package com.Mike.crud.model;

public class Skill {
    private Integer id;
    private String skill;
    private Status status;

    public void setId(Integer id) {
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setSkill(String skill) {
        this.skill=skill;
    }

    public String getSkill() {
        return skill;
    }

    public void setStatus(Status status) {
        this.status=status;
    }

    public Status getStatus() {
        return status;
    }

    public String toString() {
        return "ID: "+id+" "+skill+" Status: "+status;
    }

}