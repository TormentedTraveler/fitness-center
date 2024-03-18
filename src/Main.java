import com.sen.fitness.local.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Fitness Center");

        System.out.println("What is your position?");
        System.out.println("1 - Client");
        System.out.println("2 - Assistant");
        System.out.println("3 - Manager");
        System.out.println("4 - Director");
        int position = sc.nextInt();
        sc.nextLine();


        Person fitnessCenter;
        switch (position){
            case 1 -> fitnessCenter = new Client();
            case 2 -> fitnessCenter = new Assistant();
            case 3 -> fitnessCenter = new Manager();
            case 4 -> fitnessCenter = new Director();
            default -> fitnessCenter = null;
        }

        if (fitnessCenter == null) {
            System.out.println("No valid value chosen");
            return;
        }

        System.out.println("Please enter username:");
        String username = sc.nextLine();
        System.out.println("Please enter password:");
        String password = sc.nextLine();

        boolean loggedIn = fitnessCenter.login(username, password);
        if (!loggedIn) {
            System.out.println("Sorry we didn't find your account type or your username/password is incorrect, please try again");
            return;
        }
        System.out.println("Hello dear Client!\n" +
                "Please enter command id to interact, type 999 to close program:\n");
        while (true) {
            fitnessCenter.showMenu();
            int CommandID = sc.nextInt();
            if (CommandID == 999) {
                System.out.println("Program ended, we hope you liked it!");
                break;
            }
            fitnessCenter.doMenuCommand(CommandID);
            System.out.println();
        }
    }
}