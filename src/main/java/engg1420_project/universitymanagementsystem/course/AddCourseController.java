//Mateo
package engg1420_project.universitymanagementsystem.course;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class AddCourseController {

    @FXML public Label statusLabel;
    @FXML private TextField courseNameField;
    @FXML private TextField courseCodeField;
    @FXML private TextField subjectCodeField;
    @FXML private TextField sectionNumberField;
    @FXML private TextField teacherNameField;
    @FXML private TextField capacityField;
    @FXML private TextField lectureTimeField;
    @FXML private TextField locationField;
    @FXML private TextField finalExamDateTimeField;
    @FXML private Button goBackButton;

    @FXML
    private void handleAddCourse() {
        try {
            // Read input values from the UI fields
            String courseName = courseNameField.getText().trim();
            String subjectCode = subjectCodeField.getText().trim();
            String teacherName = teacherNameField.getText().trim();
            String lectureTime = lectureTimeField.getText().trim();
            String location = locationField.getText().trim();
            String finalExamDateTime = finalExamDateTimeField.getText().trim();

            // Parse integers
            double courseCode = Double.parseDouble(courseCodeField.getText().trim()); // Convert courseCode to double
            String sectionNumber = sectionNumberField.getText().trim(); // sectionNumber is String already
            double capacity = Double.parseDouble(capacityField.getText().trim()); // Convert capacity to double

            // Validate required fields
            if (courseName.isEmpty() || subjectCode.isEmpty() || teacherName.isEmpty() ||
                    lectureTime.isEmpty() || location.isEmpty() || finalExamDateTime.isEmpty()) {
                statusLabel.setText("Please fill all fields.");
                return;
            }

            // Create a new Course object
            Course newCourse = new Course(courseCode, courseName, subjectCode, sectionNumber,
                    teacherName, capacity, lectureTime, location, finalExamDateTime);

            // Add the course to the database using CourseManager
            boolean courseAdded = CourseManager.addCourse(newCourse);
            if (courseAdded) {
                statusLabel.setText("Course added successfully!");
            } else {
                statusLabel.setText("Course could not be added. Conflict or duplicate entry.");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid input: Course Code, Section, and Capacity must be numbers.");
        } catch (SQLException e) {
            statusLabel.setText("Database error. Please try again.");
            e.printStackTrace();
        }
    }


    @FXML
    private void goBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }
}


