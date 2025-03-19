//package com.example.engg1420facultymanagement;
package engg1420_project.universitymanagementsystem.faculty;

import engg1420_project.universitymanagementsystem.DatabaseManager;
import engg1420_project.universitymanagementsystem.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class addFacultyController {
    private Scene previousScene;
    private DatabaseManager db;
    private AnchorPane superAnchorPane;

    public addFacultyController(Scene prevtiousScene, DatabaseManager db, AnchorPane superAnchorPane) {
        this.previousScene = prevtiousScene;
        this.db = db;
        this.superAnchorPane = superAnchorPane;
    }

    @FXML
    private TextField facultyIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField degreeField;

    @FXML
    private TextField researchInterestField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField officeLocationField;

    @FXML
    private TextField coursesOfferedField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void save(ActionEvent event) {
        String[] faculty = new String[8];
        List<String> IDs = null;

        try {
            IDs = db.getColumnValues("Faculties", "Faculty ID");
            if(db.belongsToTable("Faculties", facultyIdField.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The faculty ID: " + facultyIdField.getText() +  " is already in use");
                alert.showAndWait();
            }else{
                faculty[0] = facultyIdField.getText();
                faculty[1] = nameField.getText();
                faculty[2] = degreeField.getText();
                faculty[3] = researchInterestField.getText();
                faculty[4] = emailField.getText();
                faculty[5] = officeLocationField.getText();
                faculty[6] = coursesOfferedField.getText();
                faculty[7] = passwordField.getText();

                String[] photoInfo = new String[2];
                photoInfo[0] = facultyIdField.getText();
                photoInfo[1] = "BlankProfile.png";

                try {
                    db.addRowToTable("Faculties", faculty);
                    db.addRowToTable("Photos", photoInfo);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-overview.fxml"));
                    fxmlLoader.setController(new facultyController(db, "admin", superAnchorPane));
                    AnchorPane pane = fxmlLoader.load();
                    superAnchorPane.getChildren().clear();
                    superAnchorPane.getChildren().add(pane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private void cancel(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-overview.fxml"));
            fxmlLoader.setController(new facultyController(db, "admin", superAnchorPane));
            AnchorPane pane = fxmlLoader.load();
            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        /*if (previousScene != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
        }*/
    }
}