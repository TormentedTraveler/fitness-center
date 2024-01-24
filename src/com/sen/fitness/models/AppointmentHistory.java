package com.sen.fitness.models;

import java.sql.Timestamp;

public class AppointmentHistory {
    private int userId;
    private int procedureId;
    private Timestamp appointmentTime;
    private boolean appointmentStatus;

    private String procedureName;
    private String userName;
    private int appointmentId;

    // Constructors, getters, and setters

    public AppointmentHistory(int userId, String userName, int procedureId, String procedureName, Timestamp appointmentTime, boolean appointmentStatus, int appointmentId) {
        this.userId = userId;
        this.procedureId = procedureId;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
        this.userName = userName;
        this.procedureName = procedureName;
        this.appointmentId = appointmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public Timestamp getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Timestamp appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public boolean isAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(boolean appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
