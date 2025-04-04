package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

    @FXML private Button addStudentButton;
    @FXML private Button removeStudentButton;
    @FXML private Button closeButton;

    private ObservableList<StudentCM> studentList = FXCollections.observableArrayList();
    private Course currentCourse;
    private DatabaseManager db;

    // Constructor to initialize DatabaseManager
    public ManageEnrollmentsController() {
        this.db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());
    }

    public void setCourse(Course course) throws SQLException {
        this.currentCourse = course;
        courseLabel.setText(course.getCourseName() + " - Enrolled Students");

        loadEnrolledStudents();
    }

    private void loadEnrolledStudents() throws SQLException {
        studentList.clear();
        List<String> studentIDs = db.getColumnValuesByFilter("enrollments", "student_id", "course_code", String.valueOf(currentCourse.getCourseCode()));

        for (String studentID : studentIDs) {
            List<String> studentData = db.getRow("students", "student_id", studentID);
            if (studentData != null && !studentData.isEmpty()) {
                studentList.add(new StudentCM(
                        Integer.parseInt(studentData.get(0)),
                        studentData.get(1),
                        studentData.get(2),
                        studentData.get(3),
                        studentData.get(4),
                        studentData.get(5),
                        Integer.parseInt(studentData.get(6))
                ));
            }
        }

        studentsTable.setItems(studentList);

        currentCourse.setCurrentCapacity(studentList.size());
    }


    @FXML
    private void openAddStudentWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/course/AddStudent.fxml"));
        Parent root = loader.load();

        AddStudentController controller = loader.getController();
        controller.setManageEnrollmentsController(this);

        Stage stage = new Stage();
        stage.setTitle("Add Student");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addStudentToCourse(StudentCM studentToAdd) throws SQLException {
        if (currentCourse.getCurrentCapacity() < currentCourse.getMaxCapacity()) {
            if (!studentList.contains(studentToAdd)) {
                boolean success = db.addRowToTable("enrollments", new String[]{
                        String.valueOf(currentCourse.getCourseCode()),
                        String.valueOf(studentToAdd.getStudentID())
                });

                if (success) {
                    studentList.add(studentToAdd);
                    currentCourse.setCurrentCapacity(currentCourse.getCurrentCapacity() + 1); // Increase currentCapacity
                    studentsTable.refresh();
                } else {
                    showAlert("Error", "Failed to enroll student.");
                }
            } else {
                showAlert("Duplicate Entry", "This student is already enrolled.");
            }
        } else {
            showAlert("Enrollment Full", "The course has reached its maximum capacity.");
        }
    }

    @FXML
    private void removeStudent() throws SQLException {
        StudentCM selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this student?", ButtonType.YES, ButtonType.NO);
            confirmation.setTitle("Confirm Removal");
            confirmation.setHeaderText(null);

            if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                boolean success = db.deleteRowFromTable("enrollments", "student_id", String.valueOf(selectedStudent.getStudentID()));

                if (success) {
                    studentList.remove(selectedStudent);
                    currentCourse.setCurrentCapacity(currentCourse.getCurrentCapacity() - 1); // Decrease currentCapacity
                    studentsTable.refresh();
                } else {
                    showAlert("Error", "Failed to remove student from the course.");
                }
            }
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


