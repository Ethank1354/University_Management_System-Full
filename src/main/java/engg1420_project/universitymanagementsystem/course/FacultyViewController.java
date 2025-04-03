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
        openWindow("ViewCourses.fxml", "View Courses");
    }

    @FXML
    private void viewEnrollments() {
        openWindow("ViewEnrollments.fxml", "View Enrollment");
    }

    private void openWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
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

