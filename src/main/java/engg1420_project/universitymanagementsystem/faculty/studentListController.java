package engg1420_project.universitymanagementsystem.faculty;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class studentListController {

    private DatabaseManager db;
    private Scene previousScene;
    private String course;
    private AnchorPane superAnchorPane;
    private String previousFacultyID;
    private String access;

    public studentListController(DatabaseManager db, Scene previousScene, String course, AnchorPane superAnchorPane, String facultyID, String access) {
        this.db = db;
        this.previousScene = previousScene;
        this.course = course;
        this.superAnchorPane = superAnchorPane;
        this.previousFacultyID = facultyID;
        this.access = access;
        System.out.println("Course: " + course);
    }


    @FXML
    private ListView studentsList;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() throws SQLException {

        this.course = this.course.replace("Intro ", "Introduction ");
        System.out.println("Course: " + course);
        ImageView backImage = new ImageView(new Image(HelloApplication.class.getResourceAsStream("images/backButton.png")));
        backImage.setFitHeight(30);
        backImage.setFitWidth(30);
        backButton.setGraphic(backImage);
        String[] columns = {"Subject Code"};
        List<String> course_code = db.getFilteredValues("Courses", columns, "Course Name", course);
        List<String> students = db.getColumnValuesByFilter("Students", "Name", "Subjects Registered", course_code.get(0));
        List<String> student_ID = db.getColumnValuesByFilter("Students", "Student ID", "Subjects Registered", course_code.get(0));
        List<String> values = new ArrayList<>();

        for(int i = 0; i < students.size(); i++){
            values.add(student_ID.get(i) + ":" + students.get(i));
        }

        studentsList.getItems().addAll(values);
    }

    @FXML
    void goBack(ActionEvent event) {
        try{
            // Get current stage and store previous scene
            Stage currentStage = (Stage) studentsList.getScene().getWindow();
            Scene previousScene = currentStage.getScene(); // Save current scene

            FacultyProfileController profileController = new FacultyProfileController(this.previousFacultyID, access, db, superAnchorPane, null);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
            fxmlLoader.setController(profileController);

            AnchorPane pane = fxmlLoader.load();

            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






}
