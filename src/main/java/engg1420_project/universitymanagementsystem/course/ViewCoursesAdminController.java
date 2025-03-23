//package com.example.project;
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
    private void initialize() {
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

        // Disable buttons initially
        editCourseButton.setDisable(true);
        deleteCourseButton.setDisable(true);
        manageEnrollmentsButton.setDisable(true);
        assignFacultyButton.setDisable(true);

        // Enable buttons only when a course is selected
        coursesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isSelected = newSelection != null;
            editCourseButton.setDisable(!isSelected);
            deleteCourseButton.setDisable(!isSelected);
            manageEnrollmentsButton.setDisable(!isSelected);
            assignFacultyButton.setDisable(!isSelected);
        });
    }
    //open Add Course window
    @FXML
    private void openAddCourse() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCourse.fxml"));
            Parent root = loader.load();
//            courseList.setAll(CourseManager.getCourses());
//            coursesTable.refresh();
//
//            AnchorPane pane = loader.load();
//            contentPane.getChildren().setAll(pane);

            Stage addCourseStage = new Stage();
            addCourseStage.setScene(new Scene(root));
            addCourseStage.setTitle("Add Course");
            addCourseStage.initModality(Modality.APPLICATION_MODAL);
            addCourseStage.showAndWait();

            // Refresh the course list after adding a new course
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open Edit Course Window
    @FXML
    private void openEditCourse() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCourse.fxml"));
            Parent root = loader.load();

            EditCourseController controller = loader.getController();
            controller.setCourse(selectedCourse); // Pass course to edit

            Stage editStage = new Stage();
            editStage.setScene(new Scene(root));
            editStage.setTitle("Edit Course");
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.showAndWait();

            // Refresh table after editing
            coursesTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete Course
    @FXML
    private void deleteCourse() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this course?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText(null);

        if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            CourseManager.deleteCourse(selectedCourse);
            courseList.remove(selectedCourse);
            coursesTable.refresh();
        }
    }

    // Open Manage Enrollments Window
    @FXML
    private void openManageEnrollments() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/ManageEnrollments.fxml"));

            Parent root = loader.load();

            ManageEnrollmentsController controller = loader.getController();
            controller.setCourse(selectedCourse); // Pass course to display enrolled students

            Stage enrollmentsStage = new Stage();
            enrollmentsStage.setScene(new Scene(root));
            enrollmentsStage.setTitle("Manage Enrollments");
            enrollmentsStage.initModality(Modality.APPLICATION_MODAL);
            enrollmentsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void assignFaculty() {
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
            stage.showAndWait();

            // Table should already be refreshed from AssignFacultyController
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        coursesTable.refresh();
    }

    // Close the window
    @FXML
    private void goBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }

}