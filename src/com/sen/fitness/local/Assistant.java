package com.sen.fitness.center;

import com.sen.fitness.data.Database;
import com.sen.fitness.models.AppointmentHistory;
import com.sen.fitness.models.Procedure;
import com.sen.fitness.models.UserInfo;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Assistant extends Person {
    private Database data;
    private boolean isUserLoggedIn = false;

    public Assistant() {
        this.data = new Database();
    }

    public boolean login(String name, String password) {
        int userLoggedIn = this.data.getLoggedIn(this, name, password);
        if (userLoggedIn == 1) {
            System.out.println("Login succeeded");
            isUserLoggedIn = true;
            return true;
        }
        System.out.println("Login failed");
        return false;
    }

    public void exit() {
        isUserLoggedIn = false;
        System.out.println("exited");
    }

    public void showMenu() {
        System.out.println("1 Show Procedures List");
        System.out.println("2 Find All Procedures");
        System.out.println("3 Find Client");
        System.out.println("4 Show Procedures Schedule");
        System.out.println("5 Buy Procedure");
        System.out.println("6 Find Procedure");
        System.out.println("7 Set Appointment Status");
        System.out.println("8 Exit");
    }

    public void doMenuCommand(int commandNum) {
        if (!isUserLoggedIn) {
            System.out.println("User is not logged in");
            return;
        }

        switch (commandNum) {
            case 1 -> showProcedureList();
            case 2 -> showAllProcedures();
            case 3 -> findClientByNameOrSurname();
            case 4 -> showProcedureSchedule();
            case 5 -> buyProcedure();
            case 6 -> findProcedure();
            case 7 -> setAppointmentStatus();
            case 8 -> exit();
            default -> System.out.println("No command like this");
        }
    }

    private void showProcedureList() {
        ArrayList<Procedure> procedures = this.data.getProcedures();
        if (procedures.isEmpty()) {
            System.out.println("No procedures found.");
        } else {
            System.out.println("List of Procedures:");
            for (Procedure procedure : procedures) {
                System.out.println("Procedure Name: " + procedure.getProcedureName());
                System.out.println("Procedure Cost: $" + procedure.getProcedureCost());
                System.out.println("-------------------------");
            }
        }
    }

    private void showAllProcedures() {
        ArrayList<AppointmentHistory> appointmentHistoryList = data.getAllAppointmentHistory();

        if (appointmentHistoryList.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("List of Appointments:");
            for (AppointmentHistory appointment : appointmentHistoryList) {
                System.out.println("User name: " + appointment.getUserName());
                System.out.println("Procedure Id: " + appointment.getProcedureId());
                System.out.println("Procedure name: " + appointment.getProcedureName());
                System.out.println("Appointment Time: " + appointment.getAppointmentTime());
                System.out.println("Appointment Status: " + appointment.isAppointmentStatus());
                System.out.println("-------------------------");
            }
        }
    }

    private void setAppointmentStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type appointment id:");
        int appointmentId = sc.nextInt();
        System.out.println("Type new appointment status:");
        int appointmentStatus = sc.nextInt();

        boolean updateSucceeded = this.data.setAppointmentStatus(appointmentId, appointmentStatus);
        if (updateSucceeded) {
            System.out.println("Update succeeded");
        } else {
            System.out.println("Update failed");
        }
    }


    private void showProcedureSchedule() {
        ArrayList<Procedure> procedures = this.data.getProcedures();
        if (procedures.isEmpty()) {
            System.out.println("No procedures found.");
        } else {
            System.out.println("List of Procedures:");
            for (Procedure procedure : procedures) {
                System.out.println("Procedure Name: " + procedure.getProcedureName());
                System.out.println("Procedure Time: " + procedure.getProcedureTime());
                System.out.println("-------------------------");
            }
        }
    }

    private void findClientByNameOrSurname() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name or surname of client:");
        String nameOrSurname = scanner.nextLine();

        ArrayList<UserInfo> matchingUsers = this.data.getUserByNameOrSurname(nameOrSurname);

        if (matchingUsers.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("List of Matching Users:");
            for (UserInfo user : matchingUsers) {
                System.out.println("Id: " + user.getId());
                System.out.println("Name: " + user.getName());
                System.out.println("Surname: " + user.getSurname());
                System.out.println("-------------------------");
            }
        }
    }

    private void buyProcedure() {
        ArrayList<Procedure> procedures = this.data.getProcedures();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id of user:");
        int userId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter id of procedure:");
        int procedureId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter appointment date in yyyy-mm-dd format");
        String appointment_date  = sc.nextLine();
        LocalDate currentDate = LocalDate.now();
        Timestamp appointmentTime = Timestamp.valueOf(currentDate.toString() + " " + "10:00:00");

        boolean procedureFound = false;
        for (Procedure procedure : procedures) {
            if (procedure.getId() == procedureId) {
                appointmentTime = Timestamp.valueOf(appointment_date + " " + procedure.getProcedureTime());
                procedureFound = true;
            }
        }
        if (!procedureFound) {
            System.out.println("Provided procedure id doesn't exist.");
            return;
        }

        boolean success = this.data.createAppointment(userId, procedureId, appointmentTime);
        if (success) {
            System.out.println("Created successfully");
        } else {
            System.out.println("Error happened");
        }
    }

    private void findProcedure () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter procedure name:");
        String procedureName = scanner.nextLine();

        ArrayList<Procedure> procedures = this.data.getProcedures();
        int matchingProcedures = 0;
        for (Procedure procedure : procedures) {
            if (procedure.getProcedureName().toLowerCase().contains(procedureName.toLowerCase())) {
                System.out.println("Procedure Name: " + procedure.getProcedureName());
                System.out.println("Procedure Cost: $" + procedure.getProcedureCost());
                System.out.println("Procedure Time: " + procedure.getProcedureTime());
                System.out.println("-------------------------");

                matchingProcedures+=1;
            }
        }
        if (matchingProcedures == 0) {
            System.out.println("No matching procedures");
        }
    }
}
