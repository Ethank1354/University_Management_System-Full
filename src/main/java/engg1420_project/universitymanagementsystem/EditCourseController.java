package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EditCourseController {

    @FXML private Button editCourseButton; // Declare the button

    @FXML
    private void goBack() {
        Stage stage = (Stage) editCourseButton.getScene().getWindow();
        stage.close();  // Close the current window
    }
}
