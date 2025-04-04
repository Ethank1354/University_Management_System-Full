// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import java.io.IOException;
import java.sql.SQLException;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class AdminViewSubjectsController {
    @FXML private TableView<Subjects> subjectsTable;
    @FXML private TableColumn<Subjects, String> nameColumn;
    @FXML private TableColumn<Subjects, String> codeColumn;
    @FXML private TextField nameField;
    @FXML private TextField codeField;

    private DatabaseManager dbManager;
    private ObservableList<Subjects> subjectsData = FXCollections.observableArrayList();

    public AdminViewSubjectsController() {
        dbManager = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());
    }

    @FXML
    public void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());

        // Add Actions column
        TableColumn<Subjects, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(ButtonTableCell.forTableColumn(this)); // Pass "this" controller
        subjectsTable.getColumns().add(actionsColumn);

        // Load data from database
        loadSubjectsFromDatabase();
    }

    // Load subjects from database into TableView
    private void loadSubjectsFromDatabase() {
        try {

        // Clear existing data
        subjectsData.clear();

            // Get all subjects from the "subjects" table
            List<String> names = dbManager.getColumnValues("Subjects", "Subject Name");
            List<String> codes = dbManager.getColumnValues("Subjects", "Subject Code");

            // Add subjects to the ObservableList
            for (int i = 0; i < names.size(); i++) {
                subjectsData.add(new Subjects(names.get(i), codes.get(i)));
            }

            // Update the TableView
            subjectsTable.setItems(subjectsData);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load subjects: " + e.getMessage());
        }
    }

    // Add a subject
    @FXML
    public void handleEditSubject(Subjects subject) {
        try {
            // Step 1: Load the dialog FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/engg1420_project/universitymanagementsystem/subject/subject-dialog.fxml"));
            Parent root = loader.load();

            // Step 2: Initialize the dialog controller
            SubjectDialogController dialogController = loader.getController();
            dialogController.setSubject(subject); // Pre-populate fields with existing data

            // Step 3: Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // Step 4: Check if "Save" was clicked and update the database
            if (dialogController.isSaved()) {
                String newName = dialogController.getName(); // Get values from dialog
                String newCode = dialogController.getCode();

                // Update the database
                List<String> newValues = Arrays.asList(newName, newCode);
                dbManager.updateRowInTable("Subjects", "Subject Code", subject.getCode(), newValues);

                // Refresh the TableView
                loadSubjectsFromDatabase();
            }
        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to edit subject: " + e.getMessage());
        }
    }

    @FXML
    private void openCreateDialog(ActionEvent event) {
        try {
            // Load the create-subject dialog FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/engg1420_project/universitymanagementsystem/subject/subject-dialog.fxml"));
            Parent root = loader.load();

            // Get the dialog controller
            SubjectDialogController dialogController = loader.getController();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Create New Subject");
            dialogStage.setScene(new Scene(root));

            // Show the dialog and wait for user input
            dialogStage.showAndWait();

            // Check if "Save" was clicked
            if (dialogController.isSaved()) {
                // Get values from the dialog
                String name = dialogController.getName();
                String code = dialogController.getCode();

                // Validate input
                if (name.isEmpty() || code.isEmpty()) {
                    showAlert("Error", "Name and code cannot be empty!");
                    return;
                }

                // Check for duplicate code
                if (dbManager.belongsToTable("Subjects", code)) {
                    showAlert("Error", "Subject code must be unique!");
                    return;
                }

                // Add to database
                String[] values = {name, code};
                boolean success = dbManager.addRowToTable("Subjects", values);

                if (success) {
                    loadSubjectsFromDatabase(); // Refresh TableView
                    showAlert("Success", "Subject created successfully!");
                }
            }
        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to create subject: " + e.getMessage());
        }
    }

    // Delete a subject
    @FXML
    public void handleDeleteSubject(Subjects subject) {
        try {
            boolean success = dbManager.deleteRowFromTable("Subjects", "Subject Code", subject.getCode());
            if (success) {
                loadSubjectsFromDatabase(); // Refresh TableView
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete subject: " + e.getMessage());
        }
    }

    // Show error/success alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

/*
import java.io.IOException;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminViewSubjectsController {
    private DatabaseManager dbManager;

    @FXML
    private TableView<Subjects> subjectsTable;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;

    // Make the data source static to share across controllers
    public static ObservableList<Subjects> sharedSubjectsData = FXCollections.observableArrayList();

    public AdminViewSubjectsController() {
        // Initialize the database manager with your SQLite file path
        dbManager = new DatabaseManager("test.db");
    }

    // Handle editing a subject
    public void handleEditSubject(Subjects subject) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/subject-dialog.fxml"));
            Parent root = loader.load();

            // Get the dialog controller
            SubjectDialogController dialogController = loader.getController();
            // dialogController.setSubject(subject); // Pass the subject to edit
            dialogController.setAllSubjects(sharedSubjectsData); // Initialize allSubjects
            dialogController.setSubject(subject);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));

            // Show the dialog and wait for a result
            dialogStage.showAndWait();

            // Check if "Save" was clicked
            if (dialogController.isSaved()) {
                dialogController.updateSubject();
                subjectsTable.refresh(); // Force UI refresh
            }
        } catch (IOException e) {
            // Handle the exception (e.g., show an error dialog);
            showErrorDialog("Failed to load the edit dialog: "  + e.getMessage());
        }
    }

    public void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleDeleteSubject(Subjects subject) {
        sharedSubjectsData.remove(subject);
    }

    @FXML
    public void initialize() {
        // Use lambda expressions to bind to properties
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());

        // Add Actions column with controller reference
        TableColumn<Subjects, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(ButtonTableCell.forTableColumn(this));
        subjectsTable.getColumns().add(actionsColumn);

        subjectsTable.setItems(sharedSubjectsData);
    }

    @FXML
    private void openCreateDialog(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/subject-dialog.fxml"));
            Parent root = (Parent) loader.load();

            SubjectDialogController dialogController = loader.getController();
            dialogController.setAllSubjects(sharedSubjectsData); // Initialize allSubjects
            dialogController.setSubject(new Subjects("", ""));

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();


            // Check if the dialog was saved and input is valid
            if (dialogController.isSaved()) {
                Subjects newSubject = dialogController.getSubject();
                sharedSubjectsData.add(newSubject);
            }
        } catch (IOException e) {
            showErrorDialog("Failed to load the edit dialog.");
        }
    }
}
*/
