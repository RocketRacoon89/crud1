package com.mike.crud.model;

public class Specialty {

    private Integer id;
    private String specialty;
    private Status status;

    public void setId(Integer id) {
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setSpecialty(String specialty) {
        this.specialty=specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setStatus(Status status) {
        this.status=status;
    }

    public Status getStatus() {
        return status;
    }

    public String toString() {
        return "ID: "+id+" "+specialty+" Status: "+status;
    }

}
