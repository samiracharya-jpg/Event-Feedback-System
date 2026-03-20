package com.eventfeedback.view;

import com.eventfeedback.controller.EventController;
import com.eventfeedback.model.Event;
import com.eventfeedback.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class EventView {

    private final Stage stage;
    private final EventController eventController;
    private final User currentUser;
    private TableView<Event> tableView;

    public EventView(Stage stage, User currentUser) {
        this.stage = stage;
        this.eventController = new EventController();
        this.currentUser = currentUser;
    }

    public void show() {

        // ---- HEADER ----
        Label titleLabel = new Label("MANAGE EVENTS");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label userLabel = new Label("Logged in as: " + currentUser.getUsername()
                + "  |  Role: " + currentUser.getRole());
        userLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // ---- MESSAGE LABEL ----
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");
        messageLabel.setVisible(false);

        // ---- TABLE ----
        tableView = new TableView<>();
        tableView.setStyle("-fx-font-size: 14px;");

        TableColumn<Event, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Event, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(200);

        TableColumn<Event, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        dateCol.setPrefWidth(130);

        TableColumn<Event, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationCol.setPrefWidth(180);

        TableColumn<Event, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(300);

        tableView.getColumns().addAll(idCol, titleCol, dateCol, locationCol, descCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadTableData();

        // ---- FORM FIELDS ----
        String fieldStyle = "-fx-pref-height: 38px; -fx-font-size: 14px; -fx-pref-width: 280px;";

        TextField titleField    = new TextField();
        TextField descField     = new TextField();
        TextField dateField     = new TextField();
        TextField locationField = new TextField();

        titleField.setPromptText("Event Title");
        descField.setPromptText("Description");
        dateField.setPromptText("Date (YYYY-MM-DD)");
        locationField.setPromptText("Location");

        titleField.setStyle(fieldStyle);
        descField.setStyle(fieldStyle);
        dateField.setStyle(fieldStyle);
        locationField.setStyle(fieldStyle);

        // ---- FORM LABELS ----
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold;";
        Label lTitle    = new Label("Title:");
        Label lDesc     = new Label("Description:");
        Label lDate     = new Label("Date:");
        Label lLocation = new Label("Location:");

        lTitle.setStyle(labelStyle);
        lDesc.setStyle(labelStyle);
        lDate.setStyle(labelStyle);
        lLocation.setStyle(labelStyle);

        // ---- FORM GRID ----
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(12);
        form.setAlignment(Pos.CENTER);
        form.add(lTitle,        0, 0);
        form.add(titleField,    1, 0);
        form.add(lDesc,         0, 1);
        form.add(descField,     1, 1);
        form.add(lDate,         0, 2);
        form.add(dateField,     1, 2);
        form.add(lLocation,     0, 3);
        form.add(locationField, 1, 3);

        // ---- BUTTONS ----
        String btnBase = "-fx-font-size: 14px; -fx-font-weight: bold; " +
                         "-fx-pref-width: 120px; -fx-pref-height: 40px; -fx-cursor: hand;";

        Button addBtn    = new Button("➕ Add");
        Button updateBtn = new Button("✏ Update");
        Button deleteBtn = new Button("🗑 Delete");
        Button clearBtn  = new Button("🔄 Clear");
        Button backBtn   = new Button("⬅ Back");

        addBtn.setStyle(btnBase + "-fx-background-color: #2ecc71; -fx-text-fill: white;");
        updateBtn.setStyle(btnBase + "-fx-background-color: #f39c12; -fx-text-fill: white;");
        deleteBtn.setStyle(btnBase + "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        clearBtn.setStyle(btnBase + "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        backBtn.setStyle(btnBase + "-fx-background-color: #2c3e50; -fx-text-fill: white;");

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn, backBtn);

        // ---- TABLE CLICK fills form ----
        tableView.setOnMouseClicked(e -> {
            Event selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                titleField.setText(selected.getTitle());
                descField.setText(selected.getDescription());
                dateField.setText(selected.getEventDate());
                locationField.setText(selected.getLocation());
            }
        });

        // ---- ADD ----
        addBtn.setOnAction(e -> {
            String t  = titleField.getText();
            String d  = descField.getText();
            String dt = dateField.getText();
            String l  = locationField.getText();

            if (t.isEmpty() || dt.isEmpty() || l.isEmpty()) {
                showMessage(messageLabel, "⚠ Title, Date and Location are required.", false);
                return;
            }

            boolean success = eventController.addEvent(t, d, dt, l, currentUser.getId());
            if (success) {
                showMessage(messageLabel, "✔ Event added successfully.", true);
                clearFields(titleField, descField, dateField, locationField);
                loadTableData();
            } else {
                showMessage(messageLabel, "⚠ Failed to add event.", false);
            }
        });

        // ---- UPDATE ----
        updateBtn.setOnAction(e -> {
            Event selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage(messageLabel, "⚠ Please select an event from the table first.", false);
                return;
            }

            String t  = titleField.getText();
            String d  = descField.getText();
            String dt = dateField.getText();
            String l  = locationField.getText();

            if (t.isEmpty() || dt.isEmpty() || l.isEmpty()) {
                showMessage(messageLabel, "⚠ Title, Date and Location are required.", false);
                return;
            }

            boolean success = eventController.updateEvent(selected.getId(), t, d, dt, l);
            if (success) {
                showMessage(messageLabel, "✔ Event updated successfully.", true);
                clearFields(titleField, descField, dateField, locationField);
                loadTableData();
            } else {
                showMessage(messageLabel, "⚠ Failed to update event.", false);
            }
        });

        // ---- DELETE ----
        deleteBtn.setOnAction(e -> {
            Event selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage(messageLabel, "⚠ Please select an event to delete.", false);
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Event");
            alert.setContentText("Are you sure you want to delete: " + selected.getTitle() + "?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean success = eventController.deleteEvent(selected.getId());
                    if (success) {
                        showMessage(messageLabel, "✔ Event deleted successfully.", true);
                        clearFields(titleField, descField, dateField, locationField);
                        loadTableData();
                    } else {
                        showMessage(messageLabel, "⚠ Failed to delete event.", false);
                    }
                }
            });
        });

        // ---- CLEAR ----
        clearBtn.setOnAction(e -> {
            clearFields(titleField, descField, dateField, locationField);
            messageLabel.setVisible(false);
            tableView.getSelectionModel().clearSelection();
        });

        // ---- BACK ----
        backBtn.setOnAction(e -> new MainMenuView(stage).showLoggedInMenu(currentUser));

        // ---- MAIN LAYOUT ----
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #f0f4f8;");
        layout.getChildren().addAll(
                titleLabel, userLabel,
                tableView, messageLabel,
                form, buttons);

        Scene scene = new Scene(layout);
        stage.setTitle("Manage Events - Event Feedback System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }

    private void loadTableData() {
        List<Event> events = eventController.getAllEvents();
        ObservableList<Event> data = FXCollections.observableArrayList(events);
        tableView.setItems(data);
    }

    private void showMessage(Label label, String msg, boolean success) {
        label.setText(msg);
        label.setStyle(success
                ? "-fx-text-fill: #27ae60; -fx-font-size: 14px;"
                : "-fx-text-fill: #e74c3c; -fx-font-size: 14px;");
        label.setVisible(true);
    }

    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }
}