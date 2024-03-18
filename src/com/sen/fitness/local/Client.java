package com.sen.fitness.center;

import com.sen.fitness.data.Database;
import com.sen.fitness.models.AppointmentHistory;
import com.sen.fitness.models.Procedure;
import com.sen.fitness.models.UserInfo;

import java.util.ArrayList;

public class Client extends Person {
    private Database data;
    private boolean isUserLoggedIn = false;
    private int clientId;

    public Client() {
        this.data = new Database();
    }
    public boolean login(String name, String password) {
        int clientId = this.data.getLoggedIn(this, name, password);
        if (clientId != 0) {
            isUserLoggedIn = true;
            this.clientId = clientId;
            System.out.println("Login succeeded");
            System.out.println();
            return true;
        }
        System.out.println("Login failed");
        System.out.println();
        return false;
    }

    public void exit() {
        isUserLoggedIn = false;
    }

    public void showMenu() {
        System.out.println("1 Show Visits History");
        System.out.println("2 Show Last Visit");
        System.out.println("3 Show Payment History");
        System.out.println("4 Show Training Schedule");
        System.out.println("5 Show Patient Info");
        System.out.println("6 Exit");
    }

    public void doMenuCommand(int commandNum) {
        if (!isUserLoggedIn) {
            System.out.println("User is not logged in");
            return;
        }

        switch (commandNum) {
            case 1 -> showVisitsHistory();
            case 2 -> showLastVisit();
            case 3 -> showPaymentHistory();
            case 4 -> showTrainingSchedule();
            case 5 -> showPatientInfo();
            case 6 -> exit();
            default -> System.out.println("No command like this");
        }
    }

    public void showVisitsHistory() {
        System.out.println(this.clientId);
        ArrayList<AppointmentHistory> visits = this.data.getVisitsHistory(this.clientId);

        if (visits.isEmpty()) {
            System.out.println("No visits found");
            return;
        }

        for (AppointmentHistory visit : visits) {
            System.out.println("User Name: " + visit.getUserName());
            System.out.println("Procedure Name: " + visit.getProcedureName());
            System.out.println("Appointment Time: " + visit.getAppointmentTime());
            System.out.println("-------------------------");
        }
    }

    public void showLastVisit () {
        AppointmentHistory lastVisit = this.data.getLatestVisit(clientId);

        if (lastVisit == null) {
            System.out.println("No visits found");
            return;
        }

        System.out.println("Last visit:");
        System.out.println("User Name: " + lastVisit.getUserName());
        System.out.println("Procedure Name: " + lastVisit.getProcedureName());
        System.out.println("Appointment Time: " + lastVisit.getAppointmentTime());

    }

    public void showPaymentHistory() {
        ArrayList<AppointmentHistory> visits = this.data.getVisitsHistory(this.clientId);

        if (visits.isEmpty()) {
            System.out.println("No visits found");
            return;
        }

        for (AppointmentHistory visit : visits) {
            System.out.println("User Name: " + visit.getUserName());
            System.out.println("Procedure Name: " + visit.getProcedureName());
            System.out.println("Procedure Cost: " + getProcedureCost(visit.getProcedureId())+"$" );
            System.out.println("Appointment Time: " + visit.getAppointmentTime());
            System.out.println("-------------------------");
        }
    }

    private int getProcedureCost(int procedureId){
        ArrayList<Procedure> procedures = this.data.getProcedures();
        for (Procedure procedure : procedures) {
            if (procedure.getId() == procedureId) {
                return procedure.getProcedureCost();
            }
        }
        return 0;
    }

    public void showTrainingSchedule() {
        System.out.println("Currently unavailable feature");
    }

    public void showPatientInfo() {
        UserInfo userInfo = this.data.getUserById(clientId);

        if (userInfo == null) {
            return;
        }

        System.out.println("Client Information:");
        System.out.println("Name: " + userInfo.getName());
        System.out.println("Surname: " + userInfo.getSurname());
        System.out.println("Height: " + userInfo.getHeight());
        System.out.println("Weight: " + userInfo.getWeight());
        System.out.println("Birth Date: " + userInfo.getBirthDate());
        System.out.println("Blood Type: " + userInfo.getBloodType());
    }
}
