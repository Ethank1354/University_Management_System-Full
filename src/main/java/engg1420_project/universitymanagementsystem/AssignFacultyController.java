package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AssignFacultyController {

    @FXML private Button assignFacultyButton; // Declare the button

    @FXML
    private void goBack() {
        Stage stage = (Stage) assignFacultyButton.getScene().getWindow();
        stage.close();  // Close the current window
    }
}