//Mateo
package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditCourseController {

    @FXML private TextField nameField;
    @FXML private TextField codeField;
    @FXML private TextField subjectField;
    @FXML private TextField sectionField;
    @FXML private TextField teacherField;
    @FXML private TextField capacityField;
    @FXML private TextField timeField;
    @FXML private TextField locationField;
    @FXML private TextField examField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Course course;
    private static DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    public void setCourse(Course course) {
        this.course = course;
        nameField.setText(course.getCourseName());
        codeField.setText(String.valueOf(course.getCourseCode()));
        subjectField.setText(course.getSubjectName());
        sectionField.setText(String.valueOf(course.getSectionNumber()));
        teacherField.setText(course.getTeacherName());
        capacityField.setText(String.valueOf(course.getMaxCapacity()));
        timeField.setText(course.getLectureTime());
        locationField.setText(course.getLocation());
        examField.setText(course.getFinalExamDateTime());
    }

    @FXML
    private void saveChanges() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save changes?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Save");
        confirmation.setHeaderText(null);

        if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                int courseCode = Integer.parseInt(codeField.getText());
                int sectionNumber = Integer.parseInt(sectionField.getText());
                int capacity = Integer.parseInt(capacityField.getText());

                // Update object in memory
                course.setCourseName(nameField.getText());
                course.setSubjectName(subjectField.getText());
                course.setTeacherName(teacherField.getText());
                course.setLectureTime(timeField.getText());
                course.setLocation(locationField.getText());
                course.setFinalExamDateTime(examField.getText());
                course.setCourseCode(courseCode);
                course.setSectionNumber(sectionNumber);
                course.setMaxCapacity(capacity);

                // Update the database
                // Convert Map to List<String> in correct order
                List<String> newValues = List.of(
                        nameField.getText(),
                        subjectField.getText(),
                        teacherField.getText(),
                        timeField.getText(),
                        locationField.getText(),
                        examField.getText(),
                        String.valueOf(sectionNumber),
                        String.valueOf(capacity)
                );

// Update the database using the correct method signature
                boolean updated = db.updateRowInTable("courses", "course_code", String.valueOf(courseCode), newValues);


                if (!updated) {
                    showAlert("Error", "Failed to update the course.");
                }

                // Close window
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numbers for Course Code, Section, and Capacity.");
            } catch (SQLException e) {
                showAlert("Database Error", "Failed to update the course in the database.");
            }
        }
    }

    @FXML
    private void cancelEdit() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to discard changes?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Cancel");
        confirmation.setHeaderText(null);

        if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
