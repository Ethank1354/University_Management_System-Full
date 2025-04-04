// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import java.io.IOException;
import java.sql.SQLException;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Subjects> subjectsTable;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField codeField;

    // Static list to share across controllers
    private ObservableList<Subjects> sharedSubjectsData = FXCollections.observableArrayList();
    private FilteredList<Subjects> filteredSubjects;

    private DatabaseManager dbManager = new DatabaseManager(HelloApplication.class.getResource("info.db").toString());

    @FXML
    public void initialize() {
        // Initialize filteredSubjects FIRST
        filteredSubjects = new FilteredList<>(sharedSubjectsData, p -> true);
        subjectsTable.setItems(filteredSubjects); // Bind TableView to filtered list

        // Load data from database ONCE at startup
        loadSubjectsFromDatabase();

        // Bind TableView to the shared list
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());

        // Add search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredSubjects(newValue);
        });


        // Add Actions column
        TableColumn<Subjects, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(ButtonTableCell.forTableColumn(this)); // Pass "this" controller
        subjectsTable.getColumns().add(actionsColumn);

    }

    private void updateFilteredSubjects(String searchText) {
        if (filteredSubjects == null) return; // Guard clause

        filteredSubjects.setPredicate(subject -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // Show all subjects if no search text


            }

            String lowerCaseFilter = searchText.toLowerCase();
            String subjectName = subject.getName() != null ? subject.getName().toLowerCase() : "";
            String subjectCode = subject.getCode() != null ? subject.getCode().toLowerCase() : "";

            // Return true if name or code matches the search
            return subjectName.startsWith(lowerCaseFilter) || subjectCode.startsWith(lowerCaseFilter);
        });
    }

    // Load subjects from database into TableView
    private void loadSubjectsFromDatabase() {
        try {
            // Clear existing data
            sharedSubjectsData.clear();

            // Get all subjects from the "subjects" table (load data)
            List<String> names = dbManager.getColumnValues("Subjects", "Subject Name");
            List<String> codes = dbManager.getColumnValues("Subjects", "Subject Code");

            // Add subjects to the ObservableList
            for (int i = 0; i < names.size(); i++) {
                sharedSubjectsData.add(new Subjects(names.get(i), codes.get(i)));
            }

            // Force refresh of filtered list
            updateFilteredSubjects(searchField.getText());
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
            dialogController.setAllSubjects(sharedSubjectsData); // Pass the list for uniqueness checks

            // Step 3: Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Modality blocks you from interacting with other windows while you're on the dialog
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // Step 4: Check if "Save" was clicked and update the database
            if (dialogController.isSaved()) {
                // Get the updated subject from the dialog
                Subjects updatedSubject = dialogController.getSubject();
                String newCode = updatedSubject.getCode();

                // Update the in-memory list (not the database)
                int index = sharedSubjectsData.indexOf(subject);
                sharedSubjectsData.set(index, updatedSubject);
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to edit subject: " + e.getMessage());
        }
    }

    @FXML
    private void openCreateDialog(ActionEvent event) throws IOException {
        try {
            // Load the create-subject dialog FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/engg1420_project/universitymanagementsystem/subject/subject-dialog.fxml"));
            Parent root = loader.load();

            // Get the dialog controller
            SubjectDialogController dialogController = loader.getController();
            dialogController.setAllSubjects(sharedSubjectsData);
            dialogController.setSubject(new Subjects("", "")); // Initialize empty subject

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Create New Subject");
            dialogStage.setScene(new Scene(root));

            // Show the dialog and wait for user input
            dialogStage.showAndWait();

            // Check if "Save" was clicked
            if (dialogController.isSaved()) {
                sharedSubjectsData.add(dialogController.getSubject());
            }
            } catch(IOException e){
            showAlert("Error", "Failed to load dialog.");
            }
    }

            // Delete a subject
            @FXML
            public void handleDeleteSubject (Subjects subject){
                sharedSubjectsData.remove(subject);
            }

            // Show error/success alerts
            private void showAlert (String title, String message){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setContentText(message);
                alert.showAndWait();
            }

    }

