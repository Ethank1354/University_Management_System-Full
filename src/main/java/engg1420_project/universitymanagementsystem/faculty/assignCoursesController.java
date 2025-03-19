package engg1420_project.universitymanagementsystem.faculty;

import engg1420_project.universitymanagementsystem.DatabaseManager;
import engg1420_project.universitymanagementsystem.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class assignCoursesController {

    private DatabaseManager db;
    private Scene previousScene;
    private String facultyID;
    private Faculty faculty;
    private AnchorPane superAnchorPane;

    public assignCoursesController(DatabaseManager db, Scene previousScene, String facultyID, AnchorPane superAnchorPane) {
        this.db = db;
        this.previousScene = previousScene;
        this.facultyID = facultyID;
        this.superAnchorPane = superAnchorPane;
        try {
            this.faculty = new Faculty(facultyID, db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ListView<CheckBox> courseListView;  // ListView to hold CheckBox elements


    @FXML
    public void initialize() throws SQLException {
        // Get list of course names from the database
        List<String> courses = db.getColumnValues("Courses", "Course Name");

        // Get the list of courses already assigned to the faculty
        String assignedCourses = faculty.getCourses(); // Assuming this returns a string like "course1,course2,course3"

        // Split the assigned courses string into a list of course names
        List<String> assignedCoursesList = Arrays.asList(assignedCourses.split(","));

        // Create an observable list of CheckBox elements
        ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();

        // Add a CheckBox for each course
        for (String course : courses) {
            CheckBox checkBox = new CheckBox(course);

            // Check if the course is already assigned to the faculty
            if (assignedCoursesList.contains(course)) {
                checkBox.setSelected(true); // Pre-check the checkbox if the course is already assigned
            }

            checkBoxes.add(checkBox);
        }

        // Set the items of the ListView to the list of CheckBoxes
        courseListView.setItems(checkBoxes);
    }

    @FXML
    private void save(ActionEvent event) {

        ObservableList<CheckBox> selectedCourses = courseListView.getItems();
        List<String> courses = new ArrayList<>();
        for (CheckBox checkBox : selectedCourses) {
            if (checkBox.isSelected()) {
                // Save the selected course
                courses.add(checkBox.getText());
            }
        }

        faculty.addCourses(courses);
        faculty.updateInfo();


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-overview.fxml"));
            fxmlLoader.setController(new facultyController(db, "admin", superAnchorPane));
            AnchorPane pane = fxmlLoader.load();
            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private void cancel(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-overview.fxml"));
            fxmlLoader.setController(new facultyController(db, "admin", superAnchorPane));
            AnchorPane pane = fxmlLoader.load();
            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
