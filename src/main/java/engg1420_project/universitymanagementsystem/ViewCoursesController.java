//package com.example.project;
package engg1420_project.universitymanagementsystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewCoursesController {

    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, String> nameColumn;
    @FXML
    private TableColumn<Course, Integer> codeColumn;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> sectionColumn;
    @FXML
    private TableColumn<Course, String> teacherColumn;
    @FXML
    private TableColumn<Course, Integer> capacityColumn;
    @FXML
    private TableColumn<Course, String> timeColumn;
    @FXML
    private TableColumn<Course, String> locationColumn;
    @FXML
    private TableColumn<Course, String> examColumn;

    @FXML
    private Button viewEnrollmentsButton;
    @FXML
    private Button goBackButton;

    private ObservableList<Course> courseList;

    @FXML
    private void initialize() {
        // Column bindings
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamDateTime"));

        // Load courses
        courseList = FXCollections.observableArrayList(CourseManager.getCourses());
        coursesTable.setItems(courseList);

        coursesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isSelected = newSelection != null;
            viewEnrollmentsButton.setDisable(!isSelected);
        });
    }


    @FXML
    private void viewEnrollments() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        // Open Manage Enrollments window (empty list for now)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageEnrollments.fxml"));
            Parent root = loader.load();
            Stage enrollmentsStage = new Stage();
            enrollmentsStage.setScene(new Scene(root));
            enrollmentsStage.setTitle("View Enrollments");
            enrollmentsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close the window
    @FXML
    private void goBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }
}