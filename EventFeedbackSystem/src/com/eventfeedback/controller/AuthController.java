package com.eventfeedback.controller;

import com.eventfeedback.dao.UserDAO;
import com.eventfeedback.model.User;

public class AuthController {

    private final UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    // ---- LOGIN ----
    public User login(String username, String password) {

        // Validation
        if (username == null || username.trim().isEmpty()) {
            System.out.println("[ERROR] Username cannot be empty.");
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("[ERROR] Password cannot be empty.");
            return null;
        }

        // Attempt login
        User user = userDAO.login(username.trim(), password.trim());
        if (user == null) {
            System.out.println("[ERROR] Invalid username or password.");
        }
        return user;
    }

    // ---- REGISTER ----
    public boolean register(String username, String email, String password, String confirmPassword) {

        // Validate username
        if (username == null || username.trim().isEmpty()) {
            System.out.println("[ERROR] Username cannot be empty.");
            return false;
        }
        if (username.trim().length() < 3) {
            System.out.println("[ERROR] Username must be at least 3 characters.");
            return false;
        }
        if (username.trim().contains(" ")) {
            System.out.println("[ERROR] Username cannot contain spaces.");
            return false;
        }

        // Validate email
        if (email == null || email.trim().isEmpty()) {
            System.out.println("[ERROR] Email cannot be empty.");
            return false;
        }
        if (!email.trim().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("[ERROR] Invalid email format.");
            return false;
        }

        // Validate password
        if (password == null || password.length() < 6) {
            System.out.println("[ERROR] Password must be at least 6 characters.");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            System.out.println("[ERROR] Passwords do not match.");
            return false;
        }

        // Check duplicates
        if (userDAO.usernameExists(username.trim())) {
            System.out.println("[ERROR] Username already taken.");
            return false;
        }
        if (userDAO.emailExists(email.trim())) {
            System.out.println("[ERROR] Email already registered.");
            return false;
        }

        // Save user
        User user = new User(username.trim(), password, email.trim(), "user");
        boolean success = userDAO.register(user);

        if (success) {
            System.out.println("[SUCCESS] Registration complete! You can now log in.");
        } else {
            System.out.println("[ERROR] Registration failed. Please try again.");
        }
        return success;
    }
}