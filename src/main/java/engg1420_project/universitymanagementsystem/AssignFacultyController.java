//package com.example.project;
package engg1420_project.universitymanagementsystem;

import engg1420_project.universitymanagementsystem.Course;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AssignFacultyController {

    @FXML private ComboBox<String> teacherComboBox;
    @FXML private Button assignFacultyButton;
    @FXML private Button cancelButton;

    private Course selectedCourse;
    private ViewCoursesAdminController parentController;  // Reference to update table

    private static final String[] teachers = {"Dr. Alan Turing", "Prof. Emily Brontë", "Dr. Grace Hopper", "Dr. Lakyn Copeland", "Albozr Gharabaghi"};

    public void setCourse(Course course) {
        this.selectedCourse = course;
    }

    public void setParentController(ViewCoursesAdminController controller) {
        this.parentController = controller;
    }

    @FXML
    private void initialize() {
        teacherComboBox.getItems().addAll(teachers);
    }

    @FXML
    private void assignFaculty() {
        String selectedTeacher = teacherComboBox.getValue();

        if (selectedTeacher != null && selectedCourse != null) {
            selectedCourse.setTeacherName(selectedTeacher);
            System.out.println("Assigned " + selectedTeacher + " to " + selectedCourse.getCourseName());

            // Refresh the courses table
            if (parentController != null) {
                parentController.refreshTable();
            }

            showConfirmationPopup();
            closeDialog();
        }
    }

    @FXML
    private void cancel() {
        closeDialog();
    }

    @FXML
    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        } else {
            System.out.println("Error: Stage not found.");
        }
    }


    private void showConfirmationPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Assignment Successful");
        alert.setHeaderText("Faculty Assigned Successfully");
        alert.setContentText("The teacher has been assigned to the course.");
        alert.showAndWait();
    }
}


