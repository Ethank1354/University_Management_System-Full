package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.sql.SQLException;
import java.util.List;

public class AssignFacultyController {

    @FXML private ComboBox<String> teacherComboBox;
    @FXML private Button assignFacultyButton;
    @FXML private Button cancelButton;

    private Course selectedCourse;
    private ViewCoursesAdminController parentController;  // Reference to update table

    private static DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    public void setCourse(Course course) {
        this.selectedCourse = course;
    }

    public void setParentController(ViewCoursesAdminController controller) {
        this.parentController = controller;
    }

    @FXML
    private void initialize() throws SQLException {
        populateTeacherComboBox();
    }

    private void populateTeacherComboBox() throws SQLException {
        List<String> teacherList = db.getColumnValues("faculty", "name");  // Use instance method
        teacherComboBox.setItems(FXCollections.observableArrayList(teacherList));
    }

    @FXML
    private void assignFaculty() throws SQLException {
        String selectedTeacher = teacherComboBox.getValue();

        if (selectedTeacher != null && selectedCourse != null) {
            boolean success = db.updateRowInTable( // Use `db` instance instead of static call
                    "courses",
                    "course_code",
                    String.valueOf(selectedCourse.getCourseCode()),
                    List.of(selectedTeacher)
            );

            if (success) {
                selectedCourse.setTeacherName(selectedTeacher);
                System.out.println("Assigned " + selectedTeacher + " to " + selectedCourse.getCourseName());

                if (parentController != null) {
                    parentController.refreshTable();
                }

                showConfirmationPopup();
                closeDialog();
            } else {
                showAlert("Assignment Failed", "Could not assign faculty to the course.");
            }
        } else {
            showAlert("Invalid Selection", "Please select a faculty member to assign.");
        }
    }

    @FXML
    private void cancel() {
        closeDialog();
    }

    @FXML
    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        } else {
            System.out.println("Error: Stage not found.");
        }
    }

    private void showConfirmationPopup() {
        showAlert("Assignment Successful", "The teacher has been assigned to the course.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}




