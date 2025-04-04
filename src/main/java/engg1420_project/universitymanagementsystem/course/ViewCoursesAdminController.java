package engg1420_project.universitymanagementsystem.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class ViewCoursesAdminController {

    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, Integer> codeColumn;
    @FXML private TableColumn<Course, String> subjectColumn;
    @FXML private TableColumn<Course, Integer> sectionColumn;
    @FXML private TableColumn<Course, String> teacherColumn;
    @FXML private TableColumn<Course, Integer> capacityColumn;
    @FXML private TableColumn<Course, String> timeColumn;
    @FXML private TableColumn<Course, String> locationColumn;
    @FXML private TableColumn<Course, String> examColumn;

    @FXML private Button addCourseButton;
    @FXML private Button editCourseButton;
    @FXML private Button deleteCourseButton;
    @FXML private Button manageEnrollmentsButton;
    @FXML private Button assignFacultyButton;
    @FXML private Button goBackButton;

    private ObservableList<Course> courseList;

    private AnchorPane contentPane;

    @FXML
    private void initialize() throws SQLException {
        // Initialize columns with respective property names
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamDateTime"));

        // Fetch and display courses from the database
        refreshTable();

        // Disable buttons initially
        editCourseButton.setDisable(true);
        deleteCourseButton.setDisable(true);
        manageEnrollmentsButton.setDisable(true);
        assignFacultyButton.setDisable(true);

        // Enable buttons when a course is selected
        coursesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isSelected = newSelection != null;
            editCourseButton.setDisable(!isSelected);
            deleteCourseButton.setDisable(!isSelected);
            manageEnrollmentsButton.setDisable(!isSelected);
            assignFacultyButton.setDisable(!isSelected);
        });
    }

    void refreshTable() throws SQLException {
        courseList = FXCollections.observableArrayList(CourseManager.getCourses());
        System.out.println("Total courses to be displayed: " + courseList.size()); // Debugging
        coursesTable.setItems(courseList);
        coursesTable.refresh();
    }

    // Open Add Course within the content pane
    @FXML
    private void openAddCourse() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCourse.fxml"));
            Parent root = loader.load();

            AddCourseController controller = loader.getController();
            controller.setParentController(this); // Pass the current controller instance

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Course");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open Edit Course within the content pane
    @FXML
    private void openEditCourse() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCourse.fxml"));
            Parent root = loader.load();

            EditCourseController controller = loader.getController();
            controller.setCourse(selectedCourse); // Pass course to edit

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Course");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete Course (remains the same)
    @FXML
    private void deleteCourse() throws SQLException {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            System.out.println("No course selected for deletion.");
            return;
        }

        System.out.println("Selected course for deletion: " +
                "Code=" + selectedCourse.getCourseCode() +
                ", Name=" + selectedCourse.getCourseName() +
                ", Section=" + selectedCourse.getSectionNumber());

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this course?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText(null);

        if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            boolean deleted = CourseManager.deleteCourse(selectedCourse);
            if (deleted) {
                System.out.println("Course successfully deleted from database.");
            } else {
                System.out.println("Failed to delete course from database.");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR,
                        "Failed to delete the course. Please try again.",
                        ButtonType.OK);
                errorAlert.setTitle("Delete Failed");
                errorAlert.setHeaderText(null);
                errorAlert.showAndWait();
            }
            refreshTable(); // Refresh the list from DB
        }
    }

    // Open Manage Enrollments within the content pane
    @FXML
    private void openManageEnrollments() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageEnrollments.fxml"));
            Parent root = loader.load();

            ManageEnrollmentsController controller = loader.getController();
            controller.setCourse(selectedCourse); // Pass course

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Enrollments");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void assignFaculty() throws SQLException {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignFaculty.fxml"));
            Parent root = loader.load();

            AssignFacultyController controller = loader.getController();
            controller.setCourse(selectedCourse);
            controller.setParentController(this); // Pass reference to update table

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Assign Faculty");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    // Go back to the main dashboard (closes the Course Admin view)
//    @FXML
//    private void goBack() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subject/dashboardAdmin.fxml")); // Replace with the correct FXML for your dashboard
//            Parent root = loader.load();
//            contentPane.getChildren().setAll(root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            contentPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setContentPane(AnchorPane contentPane) {
        this.contentPane = contentPane;
    }
}