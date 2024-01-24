package com.sen.fitness.models;

import java.sql.Timestamp;

public class UserInfo {
    private int id;
    private String name;
    private String surname;
    private int height;
    private int weight;
    private Timestamp birthDate;
    private String bloodType;

    public UserInfo(int id, String name, String surname, int height, int weight, Timestamp birthDate, String bloodType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.height = height;
        this.weight = weight;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    // Additional methods as needed
}