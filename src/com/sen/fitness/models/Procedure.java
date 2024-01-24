package com.sen.fitness.models;

import java.sql.Time;
import java.sql.Timestamp;

public class Procedure {
    private int id;
    private String procedureName;
    private int procedureCost;
    private Time procedureTime;

    // Constructors, getters, and setters

    public Procedure(int id, String procedureName, int procedureCost, Time procedureTime) {
        this.id = id;
        this.procedureName = procedureName;
        this.procedureCost = procedureCost;
        this.procedureTime = procedureTime;
    }

    public int getId() {
        return id;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public int getProcedureCost() {
        return procedureCost;
    }

    public void setProcedureCost(int procedureCost) {
        this.procedureCost = procedureCost;
    }

    public Time getProcedureTime() {
        return procedureTime;
    }

    public void setProcedureTime(Time procedureTime) {
        this.procedureTime = procedureTime;
    }
}
