//package com.example.project;
package engg1420_project.universitymanagementsystem;

import engg1420_project.universitymanagementsystem.Course;
import engg1420_project.universitymanagementsystem.StudentCM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageEnrollmentsController {

    @FXML private Label courseLabel;
    @FXML private TableView<StudentCM> studentsTable;
    @FXML private TableColumn<StudentCM, String> idColumn;
    @FXML private TableColumn<StudentCM, String> nameColumn;
    @FXML private TableColumn<StudentCM, String> addressColumn;
    @FXML private TableColumn<StudentCM, String> phoneColumn;
    @FXML private TableColumn<StudentCM, String> emailColumn;
    @FXML private TableColumn<StudentCM, String> levelColumn;
    @FXML private TableColumn<StudentCM, String> semesterColumn;

    @FXML private ComboBox<String> studentComboBox;
    @FXML private Button addStudentButton;
    @FXML private Button removeStudentButton;
    @FXML private Button closeButton;

    private ObservableList<StudentCM> studentList = FXCollections.observableArrayList();
    private Course currentCourse;

    public void setCourse(Course course) {
        this.currentCourse = course;
        courseLabel.setText(course.getCourseName() + " - Enrolled Students");

        // Fill the list with your hardcoded student data
        studentList.setAll(
                new StudentCM(20250001, "Alice Smith", "123 Maple St.", "555-1234", "alice@example.edu", "Undergraduate", 2025),
                new StudentCM(20250002, "Bob Johnson", "456 Oak St.", "555-5678", "bob@example.edu", "Graduate", 2025),
                new StudentCM(20250003, "Carol Williams", "789 Pine St.", "555-9012", "carol@example.edu", "Graduate", 2025),
                new StudentCM(20250004, "Lucka Racki", "1767 Jane St.", "439-9966", "lucka@example.edu", "Undergraduate", 2025),
                new StudentCM(20250005, "David Lee", "90 Elm St.", "555-3456", "david@example.edu", "Undergraduate", 2025),
                new StudentCM(20250006, "Emily Brown", "111 Oak Ave.", "555-7890", "emily@example.edu", "Graduate", 2025),
                new StudentCM(20250007, "George Smith", "222 Pine Rd.", "555-2345", "george@example.edu", "Undergraduate", 2025),
                new StudentCM(20250008, "Helen Jones", "333 Maple Dr.", "555-4567", "helen@example.edu", "Graduate", 2025),
                new StudentCM(20250009, "Isaac Clark", "444 Cedar Ln.", "555-8901", "isaac@example.edu", "Undergraduate", 2025),
                new StudentCM(20250010, "Jennifer Davis", "555 Oakwood Pl", "555-3456", "jennifer@example.edu", "Graduate", 2025)
        );

        studentsTable.setItems(studentList);

        // Populate studentComboBox with student names for adding
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for (StudentCM student : studentList) {
            studentNames.add(student.getName());
        }
        studentComboBox.setItems(studentNames);
    }

    @FXML
    private void initialize() {
        // Populate ComboBox with student names
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for (StudentCM student : studentList) {
            studentNames.add(student.getName());
        }
        studentComboBox.setItems(studentNames);
    }

    @FXML
    private void openAddStudentWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/AddStudent.fxml"));
        Parent root = loader.load();

        // Correct the controller reference passing
        AddStudentController controller = loader.getController();
        controller.setStudentList(studentList);  // Pass the student list to the new window
        controller.setManageEnrollmentsController(this);  // Pass the main controller to update the table

        Stage stage = new Stage();
        stage.setTitle("Add Student");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void addStudentToCourse(StudentCM studentToAdd) {
        studentsTable.getItems().add(studentToAdd);
    }




    @FXML
    private void removeStudent() {
        StudentCM selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to remove this student?", ButtonType.YES, ButtonType.NO);
            confirmation.setTitle("Confirm Removal");
            confirmation.setHeaderText(null);

            if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                studentsTable.getItems().remove(selectedStudent);
            }
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
