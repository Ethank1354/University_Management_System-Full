// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class SubjectDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField codeField;
    private ObservableList<Subjects> allSubjects; // Reference to sharedSubjectsData
    private Subjects subject;
    private boolean saved = false;

    /*
    public void initialize() {
        dbManager = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());
    }

     */


    // Called when opening the dialog (from AdminViewSubjectsController)
    public void setAllSubjects(ObservableList<Subjects> allSubjects) {
        this.allSubjects = allSubjects;
    }

    // Called when "Save" is clicked
    @FXML
    private void handleSave(ActionEvent event) {
        String newName = nameField.getText().trim();
        String newCode = codeField.getText().trim().toUpperCase(); // Normalize to uppercase

        // Validate input
        if (newName.isEmpty() || newCode.isEmpty()) {
            showError("Name and code cannot be empty!");
            return;
        }

        // Check for duplicates in the shared list
        boolean isDuplicate = allSubjects.stream()
                .anyMatch(s -> s!= subject && s.getCode().equalsIgnoreCase(newCode));

        if (isDuplicate) {
            showError("Subject code must be unique!");
            return;
        }

        // Update the subject
        subject.setName(nameField.getText().trim());
        subject.setCode(codeField.getText().trim().toUpperCase());
        saved = true;

        // Close the dialog
        ((Stage) codeField.getScene().getWindow()).close();
    }

    @FXML
    private void handleCancel() {
        saved = false;
        ((Stage) nameField.getScene().getWindow()).close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Getters/setters
    public boolean isSaved() {
        return saved;
    }
    public void setSubject(Subjects subject) {
        this.subject = subject;
        // Pre-fill fields with existing data
        nameField.setText(subject.getName());
        codeField.setText(subject.getCode());
    }
    public Subjects getSubject() {
        return this.subject; // Return the modified subject (NOT a new instance)
    }
    }


