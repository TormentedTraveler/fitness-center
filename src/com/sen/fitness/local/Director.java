package com.sen.fitness.center;

import com.sen.fitness.data.Database;
import com.sen.fitness.models.StuffInfo;
import com.sen.fitness.models.UserInfo;

import java.util.Scanner;

public class Director extends Person{
    private Database data;
    private boolean isUserLoggedIn = false;
    private String accountType = "4";

    public Director() {
        this.data = new Database();
    }

    @Override
    public boolean login(String name, String password) {
        int userLoggedIn = this.data.getLoggedIn(this, name, password, accountType);
        if (userLoggedIn == 1) {
            System.out.println("Login succeeded");
            isUserLoggedIn = true;
            return true;
        }
        System.out.println("Login failed");
        return false;
    }

    @Override
    public void showMenu() {
        System.out.println("1 Add Client");
        System.out.println("2 Add Stuff");
        System.out.println("3 Search Client");
        System.out.println("4 Search Stuff");
        System.out.println("5 Delete Client");
        System.out.println("6 Delete Stuff");
        System.out.println("7 Exit");
    }

    @Override
    public void doMenuCommand(int commandNum) {
        if (!isUserLoggedIn) {
            System.out.println("User is not logged in");
            return;
        }

        switch (commandNum) {
            case 1 -> addClient();
            case 2 -> addStuff();
            case 3 -> searchClient();
            case 4 -> searchStuff();
            case 5 -> deleteClient();
            case 6 -> deleteStuff();
            case 7 -> exit();
        }
    }

    public void addStuff() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter firstname:");
        String firstname = scanner.nextLine();

        System.out.println("Enter surname:");
        String surname = scanner.nextLine();

        System.out.println("Enter account type:");
        System.out.println("2 - Assistant");
        System.out.println("3 - Manager");
        System.out.println("4 - Director");
        int accountType = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        this.data.addStuff(firstname, surname, accountType, username, password);
        System.out.println("Stuff was added");
    }

    public void addClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter name:");
        String firstname = scanner.nextLine();

        System.out.println("Enter surname:");
        String surname = scanner.nextLine();

        System.out.println("Enter height:");
        int height = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter weight:");
        int weight = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter birth date (yyyy-mm-dd):");
        String birthDate = scanner.nextLine();

        System.out.println("Enter blood type:");
        String bloodType = scanner.nextLine();

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        this.data.addUser(firstname, surname, height, weight, birthDate, bloodType, username, password);
        System.out.println("User was added");
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

    public void searchStuff () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter client name:");
        String clientName = sc.nextLine();

        StuffInfo matchedClient = this.data.getStuffByName(clientName);
        if (matchedClient == null) {
            System.out.println("No stuff found");
            return;
        }

        System.out.println("Client info:");
        printStuffInfo(matchedClient);
        System.out.println("-------------------------");
    }

    private void printClientInfo(UserInfo client) {
        System.out.println("Id: " + client.getId());
        System.out.println("Name: " + client.getName());
        System.out.println("Surname: " + client.getSurname());
        System.out.println("Height: " + client.getHeight());
        System.out.println("Weight: " + client.getWeight());
        System.out.println("Birth Date: " + client.getBirthDate());
        System.out.println("Blood Type: " + client.getBloodType());
    }

    private void printStuffInfo(StuffInfo client) {
        System.out.println("Id: " + client.getId());
        System.out.println("Name: " + client.getFirstname());
        System.out.println("Surname: " + client.getSurname());
        System.out.println("Account Type: " + client.getAccountType());
    }

    public void deleteStuff () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter stuff id:");
        int stuffId = sc.nextInt();

        boolean isDeleted = this.data.deleteStuff(stuffId);
        if (isDeleted) {
            System.out.println("Stuff was deleted");
            return;
        }
        System.out.println("Something went wrong");
    }

    public void deleteClient () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter client id:");
        int clientId = sc.nextInt();

        boolean isDeleted = this.data.deleteClient(clientId);
        if (isDeleted) {
            System.out.println("Client was deleted");
            return;
        }
        System.out.println("Something went wrong");
    }
}
