package com.sen.fitness.center;

import com.sen.fitness.data.Database;
import com.sen.fitness.models.UserInfo;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends Person{
    private Database data;
    private boolean isUserLoggedIn = false;

    public Manager() {
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
        this.isUserLoggedIn = false;
    }

    public void showMenu() {
        System.out.println("1 Show Clients List");
        System.out.println("2 Show Clients Count");
        System.out.println("3 Search Client");
        System.out.println("4 Edit Procedure Cost");
        System.out.println("5 Edit Procedure Name");
        System.out.println("6 Show Maximum Visiting Client");
        System.out.println("7 Show Minimum Visiting Client");
        System.out.println("8 Exit");
    }

    public void doMenuCommand(int commandNum) {
        if (!isUserLoggedIn) {
            System.out.println("User is not logged in");
            return;
        }

        switch (commandNum) {
            case 1 -> showClientsList();
            case 2 -> showClientsCount();
            case 3 -> searchClient();
            case 4 -> editProcedureCost();
            case 5 -> editProcedureNameOrTime();
            case 6 -> showMaxVisitingClient();
            case 7 -> showMinVisitingClient();
            case 8 -> exit();
        }
    }

    public void showClientsList() {
        ArrayList<UserInfo> clientsList = this.data.getClientsList();

        if (clientsList.isEmpty()) {
            System.out.println("There is no client");
            return;
        }

        for (UserInfo client : clientsList) {
            System.out.println("Client ID: " + client.getId());
            System.out.println("Name: " + client.getName());
            System.out.println("Surname: " + client.getSurname());
            System.out.println("-------------------------");
        }
    }

    public void showClientsCount() {
        int clientsCount = this.data.getClientCount();

        System.out.println("There are " + clientsCount + " users");
    }

    public void searchClient () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter client name:");
        String clientName = sc.nextLine();

        UserInfo matchedClient = this.data.getClientByName(clientName);
        if (matchedClient == null) {
            System.out.println("No client found");
            return;
        }

        System.out.println("Client info:");
        printClientInfo(matchedClient);
        System.out.println("-------------------------");
    }

    public void editProcedureCost() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter procedure name:");
        String procedureName = sc.nextLine();
        System.out.println("Enter new procedure cost:");
        int newProcedureCost = sc.nextInt();

        boolean procedureUpdatedSuccessfully = this.data.editProcedureCost(procedureName, newProcedureCost);

        if (procedureUpdatedSuccessfully) {
            System.out.println("Procedure updated successfully");
            return;
        }

        System.out.println("No procedure with this name was found");
    }

    public void editProcedureNameOrTime() {
        Scanner sc = new Scanner(System.in);

        System.out.println("What do you want to update: \n 1 Procedure name \n 2 Procedure time \n Enter corresponding number");

        int operationNumber = Integer.parseInt(sc.nextLine());

        System.out.println("Enter procedure name");
        String procedureName = sc.nextLine();
        boolean procedureUpdatedSuccessfully = false;
        if (operationNumber == 1) {
            System.out.println("Enter new procedure name");
            String newProcedureName = sc.nextLine();

            procedureUpdatedSuccessfully = this.data.editProcedureNameOrTime(procedureName, newProcedureName);

        }else if (operationNumber == 2) {
            System.out.println("Enter new procedure time");
            String newProcedureTime = sc.nextLine();
            Time convertedNewProcedureTime = Time.valueOf(newProcedureTime);

            procedureUpdatedSuccessfully = this.data.editProcedureNameOrTime(procedureName, convertedNewProcedureTime);
        }else {
            System.out.println("There is no operation with number " + operationNumber);
            return;
        }

        if (procedureUpdatedSuccessfully) {
            System.out.println("Procedure updated successfully");
            return;
        }
        System.out.println("No procedure with this name was found");
    }

    public void showMaxVisitingClient() {
        UserInfo maximumVisitingClient = this.data.getMaximumVisitingClient();
        int maximumVisitingClientVisitsCount = this.data.getMaximumVisitingClientsVisitsCount();


        if (maximumVisitingClient == null) {
            System.out.println("There is no clients");
            return;
        }

        printClientInfo(maximumVisitingClient);
        System.out.println("Visits Count: " + maximumVisitingClientVisitsCount);

    }

    public void showMinVisitingClient() {
        UserInfo minimumVisitingClient = this.data.getMinimumVisitingClient();
        int minimumVisitingClientVisitsCount = this.data.getMinimumVisitingClientsVisitsCount();

        if (minimumVisitingClient == null) {
            System.out.println("There is no clients");
            return;
        }

        printClientInfo(minimumVisitingClient);
        System.out.println("Visits Count: " + minimumVisitingClientVisitsCount);
    }

    private void printClientInfo(UserInfo client) {
        System.out.println("Name: " + client.getName());
        System.out.println("Surname: " + client.getSurname());
        System.out.println("Height: " + client.getHeight());
        System.out.println("Weight: " + client.getWeight());
        System.out.println("Birth Date: " + client.getBirthDate());
        System.out.println("Blood Type: " + client.getBloodType());
    }
}
