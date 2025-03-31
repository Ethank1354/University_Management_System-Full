//Mateo
package engg1420_project.universitymanagementsystem.course;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FacultyViewController {

    @FXML private Button viewCoursesButton;
    @FXML private Button viewEnrollmentsButton;

    @FXML
    private void viewCourses() {
        openViewCourses("ViewCourses.fxml", "View Courses");
    }

    private void openViewCourses(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewEnrollments() {
        openViewEnrollments("ViewEnrollments.fxml", "View Enrollment");
    }

    private void openViewEnrollments(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
