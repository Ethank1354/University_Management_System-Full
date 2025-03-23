//package com.example.project;
package engg1420_project.universitymanagementsystem.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddStudentController {

    @FXML
    private ComboBox<String> studentComboBox;

    @FXML
    private Button addStudentButton;

    @FXML
    private Button closeButton;

    private ObservableList<StudentCM> studentList;
    private ManageEnrollmentsController manageEnrollmentsController;  // To reference main controller

    public void setStudentList(ObservableList<StudentCM> studentList) {
        this.studentList = studentList;
    }

    public void setManageEnrollmentsController(ManageEnrollmentsController controller) {
        this.manageEnrollmentsController = controller;
    }

    @FXML
    private void initialize() {
        if (studentList != null) {
            // Populate ComboBox with student names
            ObservableList<String> studentNames = FXCollections.observableArrayList();
            for (StudentCM student : studentList) {
                studentNames.add(student.getName());
            }
            studentComboBox.setItems(studentNames);
        } else {
            System.out.println("Student list is null.");
        }
    }


    @FXML
    private void addStudent() {
        String selectedStudentName = studentComboBox.getValue();
        if (selectedStudentName != null) {
            // Find the student object from the name
            StudentCM studentToAdd = null;
            for (StudentCM student : studentList) {
                if (student.getName().equals(selectedStudentName)) {
                    studentToAdd = student;
                    break;
                }
            }

            if (studentToAdd != null) {
                // Add student to the ManageEnrollmentsController's table
                manageEnrollmentsController.addStudentToCourse(studentToAdd);
            }
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

