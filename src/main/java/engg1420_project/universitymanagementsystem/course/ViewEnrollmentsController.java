//Mateo
package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;

public class ViewEnrollmentsController {

    @FXML private TableView<Course> enrollmentsTable;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, Double> codeColumn;
    @FXML private TableColumn<Course, String> subjectColumn;
    @FXML private TableColumn<Course, String> sectionColumn;
    @FXML private TableColumn<Course, String> teacherColumn;
    @FXML private TableColumn<Course, Double> capacityColumn;
    @FXML private TableColumn<Course, String> timeColumn;
    @FXML private TableColumn<Course, String> locationColumn;
    @FXML private TableColumn<Course, String> examColumn;
    @FXML private Button goBackButton;

    private DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    @FXML
    private void initialize() {
        setupTableColumns();
        try {
            loadEnrollmentData();
        } catch (SQLException e) {
            System.out.println("Error loading enrollment data: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamDateTime"));
    }

    private void loadEnrollmentData() throws SQLException {
        String userId = getCurrentUserId();  // You need to replace this with your actual user retrieval logic
        String userRole = getCurrentUserRole(); // Replace with your user role retrieval logic
        ObservableList<Course> courses = FXCollections.observableArrayList();

        if ("Faculty".equalsIgnoreCase(userRole)) {
            courses.addAll(getCoursesTaughtBy(userId));
        } else if ("Student".equalsIgnoreCase(userRole)) {
            courses.addAll(getCoursesEnrolledBy(userId));
        }

        enrollmentsTable.setItems(courses);
    }

    private List<Course> getCoursesTaughtBy(String facultyID) {
        return List.of();  // Return an empty list for now
    }


    private List<Course> getCoursesEnrolledBy(String studentId) {
        return List.of();  // Return an empty list for now
    }


    private Course convertToCourse(List<String> data) {
        if (data.isEmpty()) return null; // Handle empty data cases

        return new Course(
                Double.parseDouble(data.get(0)), // Course Code
                data.get(1), // Course Name
                data.get(2), // Subject Code
                data.get(3), // Section
                data.get(4), // Instructor
                Double.parseDouble(data.get(5)), // Capacity
                data.get(6), // Lecture Time
                data.get(7), // Location
                data.get(8)  // Final Exam
        );
    }

    @FXML
    private void goBack() {
        goBackButton.getScene().getWindow().hide();
    }

    // Placeholder methods - replace these with your actual user session retrieval
    private String getCurrentUserId() {
        return "12345";  // Replace with actual logic
    }

    private String getCurrentUserRole() {
        return "Student";  // Replace with actual logic
    }
}


