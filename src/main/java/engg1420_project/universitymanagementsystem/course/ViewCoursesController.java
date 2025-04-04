//Mateo
package engg1420_project.universitymanagementsystem.course;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class ViewCoursesController {

    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, String> nameColumn;
    @FXML
    private TableColumn<Course, Integer> codeColumn;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> sectionColumn;
    @FXML
    private TableColumn<Course, String> teacherColumn;
    @FXML
    private TableColumn<Course, Integer> capacityColumn;
    @FXML
    private TableColumn<Course, String> timeColumn;
    @FXML
    private TableColumn<Course, String> locationColumn;
    @FXML
    private TableColumn<Course, String> examColumn;

    @FXML
    private Button goBackButton;

    private ObservableList<Course> courseList;

    @FXML
    private void initialize() throws SQLException {
        // Column bindings
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamDateTime"));

        // Load courses
        courseList = FXCollections.observableArrayList(CourseManager.getCourses());
        coursesTable.setItems(courseList);

    }


    @FXML
    private void viewEnrollments() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course/ViewEnrollments.fxml"));
            Parent root = loader.load();
            Stage enrollmentsStage = new Stage();
            enrollmentsStage.setScene(new Scene(root));
            enrollmentsStage.setTitle("View Enrollments");
            enrollmentsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close the window
    @FXML
    private void goBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }
}