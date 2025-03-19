//package com.example.project;
package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminViewController {

    @FXML private Button viewCoursesButton;
    @FXML private Button assignFacultyButton;

    @FXML
    private void viewCourses() {
        openWindow("ViewCoursesAdmin.fxml", "View Courses");
    }

    @FXML
    private void assignFaculty() {
        openWindow("AssignFaculty.fxml", "Assign Faculty");
    }

    private void openWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


