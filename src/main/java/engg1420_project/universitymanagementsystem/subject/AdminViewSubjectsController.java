// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import java.io.IOException;
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
    @FXML
    private TableView<Subjects> subjectsTable;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;

    // Make the data source static to share across controllers
    public static ObservableList<Subjects> sharedSubjectsData = FXCollections.observableArrayList();

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
