package com.example.Advances.Banking.System.patterns.creational.singleton;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Database connection created.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void connect() {
        System.out.println("Connected to database.");
    }

    public void disconnect() {
        System.out.println("Disconnected from database.");
    }
}
