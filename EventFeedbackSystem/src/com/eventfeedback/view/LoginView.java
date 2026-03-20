package com.eventfeedback.view;

import com.eventfeedback.controller.AuthController;
import com.eventfeedback.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private final Stage stage;
    private final AuthController authController;

    public LoginView(Stage stage) {
        this.stage = stage;
        this.authController = new AuthController();
    }

    public void show() {

        // Title
        Label titleLabel = new Label("LOGIN");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Error label
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px;");
        errorLabel.setVisible(false);

        // Form fields
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-pref-height: 40px; -fx-font-size: 14px; -fx-pref-width: 300px;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-pref-height: 40px; -fx-font-size: 14px; -fx-pref-width: 300px;");

        // Grid layout for form
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);
        form.add(usernameLabel, 0, 0);
        form.add(usernameField, 1, 0);
        form.add(passwordLabel, 0, 1);
        form.add(passwordField, 1, 1);

        // Buttons
        Button loginBtn = new Button("Login");
        Button backBtn  = new Button("Back");

        String btnBase = "-fx-font-size: 14px; -fx-font-weight: bold; " +
                         "-fx-pref-width: 150px; -fx-pref-height: 42px; -fx-cursor: hand;";

        loginBtn.setStyle(btnBase + "-fx-background-color: #2ecc71; -fx-text-fill: white;");
        backBtn.setStyle(btnBase + "-fx-background-color: #95a5a6; -fx-text-fill: white;");

        // Login action
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("⚠ Please fill in all fields.");
                errorLabel.setVisible(true);
                return;
            }

            User user = authController.login(username, password);
            if (user != null) {
                new MainMenuView(stage).showLoggedInMenu(user);
            } else {
                errorLabel.setText("⚠ Invalid username or password.");
                errorLabel.setVisible(true);
                passwordField.clear();
            }
        });

        // Back action
        backBtn.setOnAction(e -> new MainMenuView(stage).show());

        // Buttons row
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(loginBtn, backBtn);

        // Main layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60));
        layout.setStyle("-fx-background-color: #f0f4f8;");
        layout.getChildren().addAll(titleLabel, errorLabel, form, buttons);

        Scene scene = new Scene(layout);
        stage.setTitle("Login - Event Feedback System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }
}