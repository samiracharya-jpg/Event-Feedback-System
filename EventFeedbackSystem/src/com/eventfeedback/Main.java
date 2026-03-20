package com.eventfeedback;

import com.eventfeedback.db.DatabaseConnection;
import com.eventfeedback.view.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize database on startup
        DatabaseConnection.getConnection();

        // Launch main menu
        MainMenuView mainMenu = new MainMenuView(primaryStage);
        mainMenu.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}