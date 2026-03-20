package com.eventfeedback.view;

import com.eventfeedback.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {

    private final Stage stage;

    public MainMenuView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        // Title
        Label titleLabel = new Label("EVENT FEEDBACK SYSTEM");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitleLabel = new Label("Welcome! Please login or register.");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");

        // Buttons
        Button loginBtn    = new Button("Login");
        Button registerBtn = new Button("Register");
        Button exitBtn     = new Button("Exit");

        String btnBase = "-fx-font-size: 15px; -fx-font-weight: bold; " +
                         "-fx-pref-width: 250px; -fx-pref-height: 50px; " +
                         "-fx-cursor: hand;";

        loginBtn.setStyle(btnBase + "-fx-background-color: #2ecc71; -fx-text-fill: white;");
        registerBtn.setStyle(btnBase + "-fx-background-color: #3498db; -fx-text-fill: white;");
        exitBtn.setStyle(btnBase + "-fx-background-color: #e74c3c; -fx-text-fill: white;");

        loginBtn.setOnAction(e -> new LoginView(stage).show());
        registerBtn.setOnAction(e -> new RegisterView(stage).show());
        exitBtn.setOnAction(e -> stage.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60));
        layout.setStyle("-fx-background-color: #f0f4f8;");
        layout.getChildren().addAll(titleLabel, subtitleLabel, loginBtn, registerBtn, exitBtn);

        Scene scene = new Scene(layout);
        stage.setTitle("Event Feedback System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }

    public void showLoggedInMenu(User user) {
        Label titleLabel = new Label("EVENT FEEDBACK SYSTEM");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label welcomeLabel = new Label("Welcome, " + user.getUsername() + "!  |  Role: " + user.getRole());
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");

        Button manageEventsBtn = new Button("Manage Events");
        Button logoutBtn       = new Button("Logout");
        Button exitBtn         = new Button("Exit");

        String btnBase = "-fx-font-size: 15px; -fx-font-weight: bold; " +
                         "-fx-pref-width: 250px; -fx-pref-height: 50px; " +
                         "-fx-cursor: hand;";

        manageEventsBtn.setStyle(btnBase + "-fx-background-color: #3498db; -fx-text-fill: white;");
        logoutBtn.setStyle(btnBase + "-fx-background-color: #e67e22; -fx-text-fill: white;");
        exitBtn.setStyle(btnBase + "-fx-background-color: #e74c3c; -fx-text-fill: white;");

        manageEventsBtn.setOnAction(e -> new EventView(stage, user).show());
        logoutBtn.setOnAction(e -> new MainMenuView(stage).show());
        exitBtn.setOnAction(e -> stage.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60));
        layout.setStyle("-fx-background-color: #f0f4f8;");
        layout.getChildren().addAll(titleLabel, welcomeLabel, manageEventsBtn, logoutBtn, exitBtn);

        Scene scene = new Scene(layout);
        stage.setTitle("Event Feedback System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }
}