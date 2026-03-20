package com.eventfeedback.view;

import com.eventfeedback.controller.AuthController;
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

public class RegisterView {

    private final Stage stage;
    private final AuthController authController;

    public RegisterView(Stage stage) {
        this.stage = stage;
        this.authController = new AuthController();
    }

    public void show() {

        // Title
        Label titleLabel = new Label("REGISTER");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Message label
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");
        messageLabel.setVisible(false);

        // Form fields
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Min 3 chars, no spaces");
        usernameField.setStyle("-fx-pref-height: 40px; -fx-font-size: 14px; -fx-pref-width: 300px;");

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        TextField emailField = new TextField();
        emailField.setPromptText("example@email.com");
        emailField.setStyle("-fx-pref-height: 40px; -fx-font-size: 14px; -fx-pref-width: 300px;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Min 6 characters");
        passwordField.setStyle("-fx-pref-height: 40px; -fx-font-size: 14px; -fx-pref-width: 300px;");

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Re-enter password");
        confirmPasswordField.setStyle("-fx-pref-height: 40px; -fx-font-size: 14px; -fx-pref-width: 300px;");

        // Grid layout
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);
        form.add(usernameLabel,        0, 0);
        form.add(usernameField,        1, 0);
        form.add(emailLabel,           0, 1);
        form.add(emailField,           1, 1);
        form.add(passwordLabel,        0, 2);
        form.add(passwordField,        1, 2);
        form.add(confirmPasswordLabel, 0, 3);
        form.add(confirmPasswordField, 1, 3);

        // Buttons
        Button registerBtn = new Button("Register");
        Button backBtn     = new Button("Back");

        String btnBase = "-fx-font-size: 14px; -fx-font-weight: bold; " +
                         "-fx-pref-width: 150px; -fx-pref-height: 42px; -fx-cursor: hand;";

        registerBtn.setStyle(btnBase + "-fx-background-color: #3498db; -fx-text-fill: white;");
        backBtn.setStyle(btnBase + "-fx-background-color: #95a5a6; -fx-text-fill: white;");

        // Register action
        registerBtn.setOnAction(e -> {
            String username        = usernameField.getText();
            String email           = emailField.getText();
            String password        = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("⚠ Please fill in all fields.");
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px;");
                messageLabel.setVisible(true);
                return;
            }

            boolean success = authController.register(
                    username, email, password, confirmPassword);

            if (success) {
                messageLabel.setText("✔ Registration successful! You can now login.");
                messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 14px;");
                messageLabel.setVisible(true);
                usernameField.clear();
                emailField.clear();
                passwordField.clear();
                confirmPasswordField.clear();
            } else {
                messageLabel.setText("⚠ Registration failed. Please check your details.");
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px;");
                messageLabel.setVisible(true);
            }
        });

        // Back action
        backBtn.setOnAction(e -> new MainMenuView(stage).show());

        // Buttons row
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(registerBtn, backBtn);

        // Main layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60));
        layout.setStyle("-fx-background-color: #f0f4f8;");
        layout.getChildren().addAll(titleLabel, messageLabel, form, buttons);

        Scene scene = new Scene(layout);
        stage.setTitle("Register - Event Feedback System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }
}