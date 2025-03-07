package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class AddCourseController {

    @FXML public Label statusLabel;
    @FXML private TextField courseNameField;
    @FXML private TextField courseCodeField;
    @FXML private TextField subjectNameField;
    @FXML private TextField sectionNumberField;
    @FXML private TextField teacherNameField;
    @FXML private TextField capacityField;
    @FXML private TextField lectureTimeField;
    @FXML private TextField locationField;
    @FXML private TextField finalExamDateTimeField;

    private List<Course> courseList = new ArrayList<>();
    // Method to handle the Add Course button click
    @FXML
    private void handleAddCourse() {
        String courseName = courseNameField.getText();
        String subjectName = subjectNameField.getText();
        String teacherName = teacherNameField.getText();
        String lectureTime = lectureTimeField.getText();
        String location = locationField.getText();
        String finalExamDateTime = finalExamDateTimeField.getText();

        // Parse integers
        int courseCode = Integer.parseInt(courseCodeField.getText());
        int sectionNumber = Integer.parseInt(sectionNumberField.getText());
        int capacity = Integer.parseInt(capacityField.getText());

        if (courseName.isEmpty() || subjectName.isEmpty() || teacherName.isEmpty() ||
                lectureTime.isEmpty() || location.isEmpty() || finalExamDateTime.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }


        Course newCourse = new Course(courseName, courseCode, subjectName, sectionNumber,
                teacherName, capacity, lectureTime, location, finalExamDateTime);

        CourseManager.addCourse(newCourse);
        // Confirm course added
        statusLabel.setText("Course added: " + newCourse);
    }
}
