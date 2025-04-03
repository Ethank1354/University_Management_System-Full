// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

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

    private Subjects subject;
    private boolean saved = false;

    private ObservableList<Subjects> allSubjects;
    private Subjects existingSubject;

    // Called when opening the dialog (from AdminViewSubjectsController)
    public void setAllSubjects(ObservableList<Subjects> subjects) {
        this.allSubjects = subjects;
    }

    // Called when "Save" is clicked
    @FXML
    private void handleSave(ActionEvent event) {
        String newCode = codeField.getText().trim().toUpperCase(); // Case-insensitive

        // Check uniqueness
        boolean isCodeUnique = isCodeUnique(newCode);

        if (!isCodeUnique) {
            showAlert("Error", "Subject code must be unique!");
            return;
        }

        // Proceed to save
        saved = true;
        updateSubject();
        closeDialog();
    }

    private boolean isCodeUnique(String newCode) {
        // For new subjects: check all codes
        if (allSubjects == null) {
            return false;
            /*
            return allSubjects.stream()
                    .noneMatch(s -> s.getCode().equalsIgnoreCase(newCode));
             */
        }
        // For edits: check codes excluding the current subject
        else {
            return allSubjects.stream()
                    .filter(s -> !s.equals(existingSubject)) // Exclude self
                    .noneMatch(s -> s.getCode().equalsIgnoreCase(newCode));
        }
    }

    private void showAlert(String error, String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }


    // Called when "Cancel" is clicked
    @FXML
    private void handleCancel(ActionEvent event) {
        saved = false;
        closeDialog();
    }

    // Close the dialog window
    private void closeDialog() {
        Stage stage = (Stage) nameField.getScene().getWindow(); // Get the stage
        stage.close(); // Close it
    }

    public boolean isSaved() {
        return saved;
    }

    public SubjectDialogController() {
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;

        if (subject != null) {
            // Editing existing subject: populate fields
            nameField.setText(subject.getName());
            codeField.setText(subject.getCode());
        } else {
            // Creating new subject: clear fields
            nameField.clear();
            codeField.clear();
        }
    }

    public void updateSubject() {
        subject.setName(nameField.getText());
        subject.setCode(codeField.getText());
    }

    public Subjects getSubject() {
        return new Subjects(nameField.getText(), codeField.getText());
    }

    // Makes sure there is actual input in the two prompts for the subject name and code
    public boolean isInputValid() {
        return !nameField.getText().isEmpty() && !codeField.getText().isEmpty();
    }
}
