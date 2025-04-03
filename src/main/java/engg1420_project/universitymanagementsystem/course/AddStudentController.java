package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class AddStudentController {

    @FXML private ComboBox<String> studentComboBox;
    @FXML private Button addStudentButton;
    @FXML private Button closeButton;

    private ObservableList<StudentCM> studentList = FXCollections.observableArrayList();
    private ManageEnrollmentsController manageEnrollmentsController;
    private DatabaseManager db;

    // Constructor to initialize DatabaseManager
    public AddStudentController() {
        this.db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());
    }

    public void setManageEnrollmentsController(ManageEnrollmentsController controller) {
        this.manageEnrollmentsController = controller;
    }

    @FXML
    private void initialize() throws SQLException {
        populateStudentComboBox();
    }

    private void populateStudentComboBox() throws SQLException {
        List<String> studentNames = db.getColumnValues("students", "name");
        studentComboBox.setItems(FXCollections.observableArrayList(studentNames));
    }

    @FXML
    private void addStudent() throws SQLException {
        String selectedStudentName = studentComboBox.getValue();

        if (selectedStudentName != null) {
            // Retrieve full student data from the database
            List<String> studentData = db.getRow("students", "name", selectedStudentName);

            if (studentData != null && !studentData.isEmpty()) {
                StudentCM studentToAdd = new StudentCM(
                        Integer.parseInt(studentData.get(0)),  // Student ID
                        studentData.get(1),  // Name
                        studentData.get(2),  // Address
                        studentData.get(3),  // Phone
                        studentData.get(4),  // Email
                        studentData.get(5),  // Academic Level
                        Integer.parseInt(studentData.get(6))   // Current Semester
                );

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

