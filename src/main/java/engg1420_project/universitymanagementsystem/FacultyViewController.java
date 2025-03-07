package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FacultyViewController {

    @FXML private Button viewCoursesButton;
    @FXML private Button viewEnrollmentButton;

    @FXML
    private void viewCourses() {
        System.out.println("Faculty is viewing courses.");
    }

    @FXML
    private void viewEnrollment() {
        System.out.println("Faculty is viewing enrollments.");
    }
}
