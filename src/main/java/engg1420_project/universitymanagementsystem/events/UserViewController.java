

//package com.example.project1;
package engg1420_project.universitymanagementsystem.events;

import java.io.IOException;

import engg1420_project.universitymanagementsystem.subject.Subjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserViewController {
    @FXML
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Subjects> subjectsTable;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;
    private ObservableList<Subjects> subjectsData = FXCollections.observableArrayList();

    public UserViewController() {
    }

    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void viewSubjects(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/example/project1/user-view-subjects.fxml"));
        Stage newStage = new Stage();
        newStage.setTitle("View Subjects");
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
