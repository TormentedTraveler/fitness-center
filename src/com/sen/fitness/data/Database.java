package com.sen.fitness.data;

import com.sen.fitness.center.Assistant;
import com.sen.fitness.center.Client;
import com.sen.fitness.center.Manager;
import com.sen.fitness.models.AppointmentHistory;
import com.sen.fitness.models.Procedure;
import com.sen.fitness.models.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Database {
    private static Connection cntn;

    public int getLoggedIn(Assistant userType, String username, String password) {
        HashMap<String, String> userCredentials = new HashMap<String, String>();
        try {
            Statement stmt = cntn.createStatement();
            String query = "select username, password from stuff_credentials where username=?";
            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userCredentials.put("username", resultSet.getString("username"));
                        userCredentials.put("password", resultSet.getString("password"));
                    }
                }
            }
            if (Objects.equals(userCredentials.get("username"), username) && Objects.equals(userCredentials.get("password"), password)) {
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public int getLoggedIn(Client userType, String username, String password) {
        HashMap<String, String> userCredentials = new HashMap<String, String>();
        int noClietExists = 0;
        try {
            String query = "select id, username, password from user_credentials where username=?";
            int clientId = 0;
            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userCredentials.put("username", resultSet.getString("username"));
                        userCredentials.put("password", resultSet.getString("password"));
                        clientId = resultSet.getInt("id");
                    }
                }
            }

            if (Objects.equals(userCredentials.get("username"), username) && Objects.equals(userCredentials.get("password"), password)) {
                return clientId;
            } else {
//                System.out.println("printing from db");
//                System.out.println(username + " " + userCredentials.get("username"));
//                System.out.println(password + " " + userCredentials.get("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("default client return");
        return noClietExists;
    }

    public int getLoggedIn(Manager userType, String username, String password) {
        HashMap<String, String> userCredentials = new HashMap<String, String>();
        int noClietExists = 0;
        try {
            String query = "select id, username, password from user_credentials where username=?";
            int clientId = 0;
            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userCredentials.put("username", resultSet.getString("username"));
                        userCredentials.put("password", resultSet.getString("password"));
                        clientId = resultSet.getInt("id");
                    }
                }
            }

            if (Objects.equals(userCredentials.get("username"), username) && Objects.equals(userCredentials.get("password"), password)) {
                return clientId;
            } else {
//                System.out.println("printing from db");
//                System.out.println(username + " " + userCredentials.get("username"));
//                System.out.println(password + " " + userCredentials.get("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("default client return");
        return noClietExists;
    }

    public ArrayList<Procedure> getProcedures() {
        ArrayList<Procedure> proceduresList = new ArrayList<>();

        try {
            String query = "SELECT id, procedure_name, procedure_cost, procedure_time FROM procedures";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String procedureName = resultSet.getString("procedure_name");
                    int procedureCost = resultSet.getInt("procedure_cost");
                    Time procedureTime = resultSet.getTime("procedure_time");

                    Procedure procedure = new Procedure(id, procedureName, procedureCost, procedureTime);

                    proceduresList.add(procedure);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proceduresList;
    }

    public ArrayList<AppointmentHistory> getAllAppointmentHistory() {
        ArrayList<AppointmentHistory> appointmentHistoryList = new ArrayList<>();

        try {
            String query = "SELECT ah.user_id, ui.name AS user_name, ah.procedure_id, p.procedure_name, ah.appointment_time, ah.appointment_status, ah.appointment_id " +
                    "FROM appointment_history AS ah " +
                    "JOIN user_info AS ui ON ah.user_id = ui.id " +
                    "JOIN procedures AS p ON ah.procedure_id = p.id";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String userName = resultSet.getString("user_name");
                    int procedureId = resultSet.getInt("procedure_id");
                    String procedureName = resultSet.getString("procedure_name");
                    Timestamp appointmentTime = resultSet.getTimestamp("appointment_time");
                    boolean appointmentStatus = resultSet.getBoolean("appointment_status");
                    int appointmentId = resultSet.getInt("appointment_id");

                    AppointmentHistory appointmentHistory = new AppointmentHistory(userId, userName, procedureId, procedureName, appointmentTime, appointmentStatus, appointmentId);
                    appointmentHistoryList.add(appointmentHistory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentHistoryList;
    }

    public ArrayList<UserInfo> getUserByNameOrSurname(String nameOrSurname) {
        ArrayList<UserInfo> matchingUsers = new ArrayList<>();

        try {
            String query = "SELECT id, name, surname, height, weight, birth_date, blood_type " +
                    "FROM user_info " +
                    "WHERE name LIKE ? OR surname LIKE ?";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + nameOrSurname + "%");
                preparedStatement.setString(2, "%" + nameOrSurname + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        int height = resultSet.getInt("height");
                        int weight = resultSet.getInt("weight");
                        Timestamp birthDate = resultSet.getTimestamp("birth_date");
                        String bloodType = resultSet.getString("blood_type");

                        UserInfo userInfo = new UserInfo(id, name, surname, height, weight, birthDate, bloodType);
                        matchingUsers.add(userInfo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchingUsers;
    }

    public boolean createAppointment(int userId, int procedureId, Timestamp appointmentTime) {
        try {
            String query = "INSERT INTO appointment_history (user_id, procedure_id, appointment_time) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, procedureId);
                preparedStatement.setTimestamp(3, appointmentTime);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setAppointmentStatus(int appointmentId, int appointmentStatus) {
        try {
            String query = "UPDATE appointment_history" +
                    "SET appointment_status = ?" +
                    "WHERE appointment_id = ?;";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setInt(1, appointmentStatus);
                preparedStatement.setInt(2, appointmentId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<AppointmentHistory> getVisitsHistory(int clientId) {
        ArrayList<AppointmentHistory> appointmentHistoryList = new ArrayList<>();

        try {
            String query = "SELECT ah.user_id, ui.name AS user_name, ah.procedure_id, p.procedure_name, ah.appointment_time, ah.appointment_status ah.appointment_id " +
                    "FROM appointment_history AS ah " +
                    "JOIN user_info AS ui ON ah.user_id = ui.id " +
                    "JOIN procedures AS p ON ah.procedure_id = p.id " +
                    "WHERE ah.user_id = ?";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setInt(1, clientId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int userId = resultSet.getInt("user_id");
                        String userName = resultSet.getString("user_name");
                        int procedureId = resultSet.getInt("procedure_id");
                        String procedureName = resultSet.getString("procedure_name");
                        Timestamp appointmentTime = resultSet.getTimestamp("appointment_time");
                        boolean appointmentStatus = resultSet.getBoolean("appointment_status");
                        int appointmentId = resultSet.getInt("appointment_id");

                        AppointmentHistory appointmentHistory = new AppointmentHistory(userId, userName, procedureId, procedureName, appointmentTime, appointmentStatus, appointmentId);
                        appointmentHistoryList.add(appointmentHistory);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentHistoryList;
    }

    public AppointmentHistory getLatestVisit(int clientId) {
        AppointmentHistory latestAppointment = null;

        try {
            String query = "SELECT ah.user_id, ui.name AS user_name, ah.procedure_id, p.procedure_name, ah.appointment_time, ah.appointment_status ah.appointment_id" +
                    "FROM appointment_history AS ah " +
                    "JOIN user_info AS ui ON ah.user_id = ui.id " +
                    "JOIN procedures AS p ON ah.procedure_id = p.id " +
                    "WHERE ah.user_id = ? " +
                    "ORDER BY ah.appointment_time DESC " +
                    "LIMIT 1";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setInt(1, clientId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int userId = resultSet.getInt("user_id");
                        String userName = resultSet.getString("user_name");
                        int procedureId = resultSet.getInt("procedure_id");
                        String procedureName = resultSet.getString("procedure_name");
                        Timestamp appointmentTime = resultSet.getTimestamp("appointment_time");
                        boolean appointmentStatus = resultSet.getBoolean("appointment_status");
                        int appointmentId = resultSet.getInt("appointment_id");

                        latestAppointment = new AppointmentHistory(userId, userName, procedureId, procedureName, appointmentTime, appointmentStatus, appointmentId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return latestAppointment;
    }

    public UserInfo getUserById(int userId) {
        UserInfo matchedUser = null;

        try {
            String query = "SELECT id, name, surname, height, weight, birth_date, blood_type " +
                    "FROM user_info " +
                    "WHERE id = ? " +
                    "LIMIT 1";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        int height = resultSet.getInt("height");
                        int weight = resultSet.getInt("weight");
                        Timestamp birthDate = resultSet.getTimestamp("birth_date");
                        String bloodType = resultSet.getString("blood_type");

                        matchedUser = new UserInfo(id, name, surname, height, weight, birthDate, bloodType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchedUser;
    }

    public ArrayList<UserInfo> getClientsList () {
        ArrayList<UserInfo> clientsList = new ArrayList<>();

        try {
            String query = "SELECT id, name, surname, height, weight, birth_date, blood_type FROM user_info";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    int height = resultSet.getInt("height");
                    int weight = resultSet.getInt("weight");
                    Timestamp birthDate = resultSet.getTimestamp("birth_date");
                    String bloodType = resultSet.getString("blood_type");

                    UserInfo client = new UserInfo(id, name, surname, height, weight, birthDate, bloodType);
                    clientsList.add(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientsList;
    }

    public int getClientCount() {
        int userCount = 0;

        try {
            String query = "SELECT COUNT(*) as count FROM user_info";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    userCount = resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userCount;
    }

    public UserInfo getClientByName(String firstname) {
        UserInfo matchedUser = null;

        try {
            String query = "SELECT id, name, surname, height, weight, birth_date, blood_type " +
                    "FROM user_info " +
                    "WHERE name LIKE ?" +
                    "LIMIT 1";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + firstname + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        int height = resultSet.getInt("height");
                        int weight = resultSet.getInt("weight");
                        Timestamp birthDate = resultSet.getTimestamp("birth_date");
                        String bloodType = resultSet.getString("blood_type");

                        matchedUser = new UserInfo(id, name, surname, height, weight, birthDate, bloodType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchedUser;
    }

    public boolean editProcedureCost (String procedureName, int newCost) {
        try {
            String query = "UPDATE procedures SET procedure_cost = ? WHERE procedure_name = ?";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setInt(1, newCost);
                preparedStatement.setString(2, procedureName);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean editProcedureNameOrTime (String procedureName, String newProcedureName) {
        try {
            String query = "UPDATE procedures SET procedure_name = ? WHERE procedure_name = ?";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setString(1, newProcedureName);
                preparedStatement.setString(2, procedureName);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editProcedureNameOrTime (String procedureName, Time newProcedureTime) {
        try {
            String query = "UPDATE procedures SET procedure_time = ? WHERE procedure_name = ?";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setTime(1, newProcedureTime);
                preparedStatement.setString(2, procedureName);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserInfo getMaximumVisitingClient() {
        UserInfo matchedUser = null;
        try {
            String query = " SELECT ui.*, ah.visit_count" +
                    "FROM user_info ui" +
                    "JOIN (" +
                    "    SELECT user_id, COUNT(*) AS visit_count" +
                    "    FROM appointment_history" +
                    "    GROUP BY user_id" +
                    "    ORDER BY visit_count DESC" +
                    "    LIMIT 1" +
                    ") ah ON ui.id = ah.user_id;";
            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    int height = resultSet.getInt("height");
                    int weight = resultSet.getInt("weight");
                    Timestamp birthDate = resultSet.getTimestamp("birth_date");
                    String bloodType = resultSet.getString("blood_type");

                    matchedUser = new UserInfo(id, name, surname, height, weight, birthDate, bloodType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchedUser;
    }

    public int getMaximumVisitingClientsVisitsCount () {
        int visitsCount = 0;
        try {
            String query = "ah.visit_count" +
                    "FROM user_info ui" +
                    "JOIN (" +
                    "    SELECT user_id, COUNT(*) AS visit_count" +
                    "    FROM appointment_history" +
                    "    GROUP BY user_id" +
                    "    ORDER BY visit_count DESC" +
                    "    LIMIT 1" +
                    ") ah ON ui.id = ah.user_id;";
            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    visitsCount = resultSet.getInt("visit_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitsCount;
    }

    public UserInfo getMinimumVisitingClient() {
        UserInfo matchedUser = null;
        try {
            String query = "ah.visit_count" +
                    "FROM user_info ui" +
                    "JOIN (" +
                    "    SELECT user_id, COUNT(*) AS visit_count" +
                    "    FROM appointment_history" +
                    "    GROUP BY user_id" +
                    "    ORDER BY visit_count ASC" +
                    "    LIMIT 1" +
                    ") ah ON ui.id = ah.user_id;";
            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    int height = resultSet.getInt("height");
                    int weight = resultSet.getInt("weight");
                    Timestamp birthDate = resultSet.getTimestamp("birth_date");
                    String bloodType = resultSet.getString("blood_type");

                    matchedUser = new UserInfo(id, name, surname, height, weight, birthDate, bloodType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchedUser;
    }

    public int getMinimumVisitingClientsVisitsCount () {
        int visitsCount = 0;
        try {
            String query = " SELECT ui.*, ah.visit_count" +
                    "FROM user_info ui" +
                    "JOIN (" +
                    "    SELECT user_id, COUNT(*) AS visit_count" +
                    "    FROM appointment_history" +
                    "    GROUP BY user_id" +
                    "    ORDER BY visit_count ASC" +
                    "    LIMIT 1" +
                    ") ah ON ui.id = ah.user_id;";
            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    visitsCount = resultSet.getInt("visit_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitsCount;
    }

    public boolean deletePerson (int personId) {
        try {
            String query = "UPDATE procedures SET procedure_time = ? WHERE procedure_name = ?";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setTime(1, newProcedureTime);
                preparedStatement.setString(2, procedureName);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addStaff (String firstname, String surname, String username,  String password, int accountType) {
        try {
            String query = "UPDATE procedures SET procedure_time = ? WHERE procedure_name = ?";

            try (PreparedStatement preparedStatement = cntn.prepareStatement(query)) {
                preparedStatement.setTime(1, newProcedureTime);
                preparedStatement.setString(2, procedureName);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12678329";
            String username = "sql12678329";
            String password = "sdkFRN1ilj";
            cntn = DriverManager.getConnection(jdbcUrl, username, password);
//            Statement stmt = conn.createStatement();
//            ResultSet resultSet = stmt.executeQuery("select * from account_types");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(2));
//            }
//            System.out.println("Successful connection");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
