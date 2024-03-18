package com.sen.fitness.models;

import java.sql.Timestamp;

public class StuffInfo {
    private int id;
    private String firstname;
    private String surname;
    private int accountType;

    public StuffInfo(int id, String firstname, String surname, int accountType) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.accountType = accountType;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}
