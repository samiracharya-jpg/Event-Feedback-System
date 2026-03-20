package com.eventfeedback.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String HOST     = "localhost";
    private static final String PORT     = "3306";
    private static final String DATABASE = "eventfeedback_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "cacTus@0103"; // 👈 your password here

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&autoReconnect=true";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            // Reconnect if connection is null or closed
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("MySQL Database connected successfully.");
                initializeTables();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }

    private static void initializeTables() {
        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id       INT PRIMARY KEY AUTO_INCREMENT,
                    username VARCHAR(50)  NOT NULL UNIQUE,
                    password VARCHAR(100) NOT NULL,
                    email    VARCHAR(100) NOT NULL UNIQUE,
                    role     VARCHAR(20)  NOT NULL DEFAULT 'user'
                );
                """;

        String createEventsTable = """
                CREATE TABLE IF NOT EXISTS events (
                    id          INT PRIMARY KEY AUTO_INCREMENT,
                    title       VARCHAR(100) NOT NULL,
                    description TEXT,
                    event_date  DATE         NOT NULL,
                    location    VARCHAR(100) NOT NULL,
                    created_by  INT,
                    FOREIGN KEY (created_by) REFERENCES users(id)
                );
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createEventsTable);
            System.out.println("Tables initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }
}