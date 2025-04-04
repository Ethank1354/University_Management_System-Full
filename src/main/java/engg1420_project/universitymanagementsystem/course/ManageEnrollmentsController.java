package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.LinkedHashMap;
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
    @FXML private ComboBox<String> studentComboBox;

    @FXML private Button addStudentButton;
    @FXML private Button removeStudentButton;
    @FXML private Button closeButton;

    private ObservableList<StudentCM> studentList = FXCollections.observableArrayList();
    private Course currentCourse;
    private DatabaseManager db;
    private boolean enrollmentsTableExists = false;

    public ManageEnrollmentsController() {
        this.db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());
    }

    public void setCourse(Course course) throws SQLException {
        this.currentCourse = course;
        courseLabel.setText(course.getCourseName() + " - Enrolled Students");

        // Check if Enrollments table exists and create it if not
        if (!enrollmentsTableExists) {
            createEnrollmentsTable();
            enrollmentsTableExists = true; // Prevent repeated creation attempts
        }

        // Initialize Table Columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().studentIDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        levelColumn.setCellValueFactory(cellData -> cellData.getValue().academicLevelProperty());
        semesterColumn.setCellValueFactory(cellData -> cellData.getValue().currentSemesterProperty());

        loadEnrolledStudents();
        populateStudentComboBox();
    }

    private void createEnrollmentsTable() {
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("Course Code", "REAL NOT NULL");
        columns.put("Student ID", "TEXT NOT NULL");

        try {
            db.createTable("Enrollments", columns);
            System.out.println("Enrollments table created.");

            // Add foreign key constraints
            String foreignKeyCourse = "ALTER TABLE Enrollments ADD CONSTRAINT fk_course FOREIGN KEY (`Course Code`) REFERENCES Courses(`Course Code`);";
            String foreignKeyStudent = "ALTER TABLE Enrollments ADD CONSTRAINT fk_student FOREIGN KEY (`Student ID`) REFERENCES Students(`Student ID`);";
            String primaryKey = "ALTER TABLE Enrollments ADD CONSTRAINT pk_enrollment PRIMARY KEY (`Course Code`, `Student ID`);";

            try (java.sql.Statement stmt = db.getConnection().createStatement()) {
                stmt.execute(foreignKeyCourse);
                stmt.execute(foreignKeyStudent);
                stmt.execute(primaryKey);
                System.out.println("Enrollments table constraints added.");
            } catch (SQLException e) {
                System.err.println("Error adding constraints to Enrollments table: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Error creating Enrollments table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadEnrolledStudents() throws SQLException {
        studentList.clear();
        double courseCodeDouble = currentCourse.getCourseCode();
        String courseCodeStringWithDecimal = String.valueOf(courseCodeDouble);
        String courseCodeStringWithoutDecimal = String.valueOf((int) courseCodeDouble); // Convert to int to remove decimal

        System.out.println("Attempting to load students for Course Code (with decimal): [" + courseCodeStringWithDecimal + "]");
        List<String> studentIDs = db.getColumnValuesByExactFilter("Enrollments", "Student ID", "Course Code", courseCodeStringWithDecimal);
        System.out.println("Retrieved Student IDs after comparison with decimal: " + studentIDs);

        if (studentIDs.isEmpty()) {
            System.out.println("Attempting to load students for Course Code (without decimal): [" + courseCodeStringWithoutDecimal + "]");
            studentIDs = db.getColumnValuesByExactFilter("Enrollments", "Student ID", "Course Code", courseCodeStringWithoutDecimal);
            System.out.println("Retrieved Student IDs after comparison without decimal: " + studentIDs);
        }

        for (String studentID : studentIDs) {
            List<String> studentData = db.getRow("Students", "Student ID", studentID);
            if (studentData != null && studentData.size() >= 7) {
                studentList.add(new StudentCM(
                        studentData.get(0),
                        studentData.get(1),
                        studentData.get(2),
                        studentData.get(3),
                        studentData.get(4),
                        studentData.get(5),
                        studentData.get(6)// Current Semester
                ));
            }
        }

        studentsTable.setItems(studentList);
        currentCourse.setCurrentCapacity(studentList.size());
    }

    private void populateStudentComboBox() throws SQLException {
        List<String> studentNames = db.getColumnValues("Students", "Name");
        studentComboBox.setItems(FXCollections.observableArrayList(studentNames));
    }

    @FXML
    private void addStudent() throws SQLException {
        String selectedStudentName = studentComboBox.getValue();

        if (selectedStudentName != null) {
            List<String> studentData = db.getRow("Students", "Name", selectedStudentName);

            if (studentData != null && studentData.size() >= 7) {
                StudentCM studentToAdd = new StudentCM(
                        studentData.get(0),
                        studentData.get(1),
                        studentData.get(2),
                        studentData.get(3),
                        studentData.get(4),
                        studentData.get(5),
                        studentData.get(6)  // Current Semester
                );
                // Directly add to the GUI list
                studentList.add(studentToAdd);
                studentsTable.setItems(studentList);
                currentCourse.setCurrentCapacity(studentList.size());
            }
        }
    }

    @FXML
    private void removeStudent() {
        StudentCM selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            studentList.remove(selectedStudent);
            studentsTable.setItems(studentList);
            currentCourse.setCurrentCapacity(studentList.size());
            studentsTable.getSelectionModel().clearSelection();
        } else {
            showAlert("Error", "Please select a student to remove.");
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