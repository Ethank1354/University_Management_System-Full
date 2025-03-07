package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StudentViewController {

    @FXML private Button viewCoursesButton;
    @FXML private Button viewEnrollmentButton;

    @FXML
    private void viewCourses() {
        // code for viewing enrolled courses
        System.out.println("Student is viewing enrolled courses.");
    }

    @FXML
    private void viewEnrollment() {
        // code for viewing enrollment
        System.out.println("Student is viewing enrollment.");
    }
}
