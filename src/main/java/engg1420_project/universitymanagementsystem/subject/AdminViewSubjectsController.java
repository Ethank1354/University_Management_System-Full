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
    private final ObservableList<Subjects> subjectsData = FXCollections.observableArrayList();

    public AdminViewSubjectsController() {
    }

    @FXML
    public void handleEditSubject(Subjects subject) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("subject-dialog.fxml"));
        Parent root = null;

        try {
            root = (Parent)loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SubjectDialogController dialogController = (SubjectDialogController)loader.getController();
        dialogController.setSubject(subject);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();
        if (dialogController.isInputValid()) {
            dialogController.updateSubject();
            this.subjectsTable.refresh();
        }

    }

    @FXML
    public void handleDeleteSubject(Subjects subject) {
        this.subjectsData.remove(subject);
    }

    @FXML
    public void initialize() {
        this.nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.codeColumn.setCellValueFactory(new PropertyValueFactory("code"));
        this.subjectsData.add(new Subjects("Mathematics", "MATH101"));
        this.subjectsData.add(new Subjects("Computer Science", "COMP200"));
        this.subjectsData.add(new Subjects("Physics", "PHYS150"));
        this.subjectsTable.setItems(this.subjectsData);
        this.subjectsTable.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Parent root = newScene.getRoot();
                root.getProperties().put("controller", this);
            }

        });
    }

    @FXML
    private void openCreateDialog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("subject-dialog.fxml"));
        Parent root = (Parent)loader.load();
        SubjectDialogController dialogController = (SubjectDialogController)loader.getController();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();
        if (dialogController.isInputValid()) {
            Subjects newSubject = dialogController.getSubject();
            this.subjectsData.add(newSubject);
        }

    }

    @FXML
    private void openSubjectDialog(Subjects subject) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("subject-dialog.fxml"));
        Parent root = (Parent)loader.load();
        SubjectDialogController dialogController = (SubjectDialogController)loader.getController();
        dialogController.setSubject(subject);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if (dialogController.isInputValid()) {
            if (subject == null) {
                this.subjectsData.add(dialogController.getSubject());
            } else {
                this.subjectsTable.refresh();
            }
        }

    }
}
