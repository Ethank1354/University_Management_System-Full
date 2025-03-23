//package com.example.project;
package engg1420_project.universitymanagementsystem.course;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    public void setCourse(Course course) {
        this.course = course;
        nameField.setText(course.getCourseName());
        codeField.setText(String.valueOf(course.getCourseCode()));
        subjectField.setText(course.getSubjectName());
        sectionField.setText(String.valueOf(course.getSectionNumber()));
        teacherField.setText(course.getTeacherName());
        capacityField.setText(String.valueOf(course.getCapacity()));
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
            course.setCourseName(nameField.getText());
            course.setSubjectName(subjectField.getText());
            course.setTeacherName(teacherField.getText());
            course.setLectureTime(timeField.getText());
            course.setLocation(locationField.getText());
            course.setFinalExamDateTime(examField.getText());

            try {
                course.setCourseCode(Integer.parseInt(codeField.getText()));
                course.setSectionNumber(Integer.parseInt(sectionField.getText()));
                course.setCapacity(Integer.parseInt(capacityField.getText()));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Skipping invalid fields.");
            }

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
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
}
