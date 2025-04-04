package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.Main;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
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
    public void initialize() throws SQLException {
        List<String> courseNames = db.getColumnValues("Courses", "Course Name");
        List<String> subjectCode = db.getColumnValues("Courses", "Subject Code");

        List<String> courses = new ArrayList<>();

        for(int i = 0; i < courseNames.size(); i++) {
            courses.add(courseNames.get(i) + ": " + subjectCode.get(i));
        }

        listViewCourses.getItems().addAll(courses);

        listViewCourses.setCellFactory(lv -> {

            ListCell<String> cell = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();

           /*
           change to not delete the student
            */
            MenuItem dropItem = new MenuItem();
            dropItem.textProperty().bind(Bindings.format("Add \"%s\" to Courses", cell.itemProperty()));
            dropItem.setOnAction(event -> {
                String item = cell.getItem();

                String[] parts = item.split(": ");

                try {
                    addCourse(parts[0]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });

            //Adding all the options to the click down menu
            contextMenu.getItems().addAll(dropItem);

            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            return cell;
        });

    }

    private void addCourse(String item) throws SQLException {
        ArrayList<String> currentCourses = student.getSubjects();

        for (int i = 0; i < currentCourses.size(); i++) {
            if (currentCourses.get(i).equals(item)) {

            } else {
                student.addSubject(item);
            }
        }
        student.updateStudent();

        try {
            String studentInfo = student.getStudentID() + ":" + student.getName();
            editStudentProfile editStudentProfile = new editStudentProfile(db, studentInfo, access, username);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/studentProfileEdit.fxml"));
            fxmlLoader.setController(editStudentProfile);
            AnchorPane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void exit (ActionEvent event) throws IOException {
        try {
            String studentInfo = student.getStudentID() + ":" + student.getName();
            editStudentProfile editStudentProfile = new editStudentProfile(db, studentInfo, access, username);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/studentProfileEdit.fxml"));
            fxmlLoader.setController(editStudentProfile);
            AnchorPane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
