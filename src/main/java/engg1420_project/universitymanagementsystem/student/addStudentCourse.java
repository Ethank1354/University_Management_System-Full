package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class addStudentCourse {

    private DatabaseManager db;
    private Student student;
    private String username;
    private String access;


    public addStudentCourse(DatabaseManager db, Student student, String username, String access) {
        this.db = db;
        this.student = student;
        this.username = username;
        this.access = access;
    }

    @FXML
    private Button btnAddCourse, btnExit;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private ListView<String> listViewCourses;

    @FXML
    public void initialize() {
        List<String> courseNames = db.getColumnValues("Courses", "Course Name");
        List<String> subjectCode = db.getColumnValues("Courses", "Subject Code");

        List<String> courses = new ArrayList<>();

        for(int i = 0; i < courseNames.size(); i++) {
            courses.add(courseNames.get(i) + ": " + subjectCode.get(i));
        }

        listViewCourses.getItems().addAll(courses);



    }

}
