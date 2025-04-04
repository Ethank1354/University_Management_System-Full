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
        // Use a specific format to convert the double to a String for comparison
        String courseCodeString = String.format("%.1f", currentCourse.getCourseCode()); // Example: one decimal place
        System.out.println("Loading students for Course Code: [" + courseCodeString + "]"); // Add logging
        List<String> studentIDs = db.getColumnValuesByExactFilter("Enrollments", "Student ID", "Course Code", courseCodeString);
        System.out.println("Retrieved Student IDs: " + studentIDs); // Add logging

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
                addStudentToCourse(studentToAdd);
            }
        }
    }

    public void addStudentToCourse(StudentCM studentToAdd) throws SQLException {
        if (currentCourse.getCurrentCapacity() < currentCourse.getCapacity()) {
            boolean alreadyEnrolled = false;
            for (StudentCM student : studentList) {
                if (student.getStudentID().equals(studentToAdd.getStudentID())) {
                    alreadyEnrolled = true;
                    break;
                }
            }
            if (!alreadyEnrolled) {
                String courseCode = String.valueOf(currentCourse.getCourseCode());
                String studentId = studentToAdd.getStudentID();
                System.out.println("Attempting to enroll student ID: [" + studentId + "] in course code: [" + courseCode + "]");
                boolean success = db.addRowToTable("Enrollments", new String[]{courseCode, studentId});

                if (success) {
                    System.out.println("Successfully enrolled student ID: [" + studentId + "] in course code: [" + courseCode + "]");
                    studentList.add(studentToAdd);
                    currentCourse.setCurrentCapacity(studentList.size());
                    studentsTable.refresh();
                } else {
                    System.out.println("Error enrolling student ID: [" + studentId + "] in course code: [" + courseCode + "]");
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
                String studentIdToRemove = selectedStudent.getStudentID();
                String courseCodeToRemove = String.valueOf(currentCourse.getCourseCode()); // Convert to String

                System.out.println("Attempting to remove student with ID: [" + studentIdToRemove + "] from course code: [" + courseCodeToRemove + "]");
                // Try to delete based on both Student ID and Course Code (as a combined filter - this is a workaround)
                boolean success = db.deleteRowFromTable("Enrollments", "Student ID", studentIdToRemove);
                if (success) {
                    studentList.remove(selectedStudent);
                    currentCourse.setCurrentCapacity(studentList.size());
                    studentsTable.refresh();
                } else {
                    // As a fallback, try to be more specific in the error message
                    showAlert("Error", "Failed to remove the student. Please ensure the student is enrolled in this specific course.");
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