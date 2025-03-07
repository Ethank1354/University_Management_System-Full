package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewEnrollmentsController {

    @FXML private Button viewEnrollmentsButton; // Declare the button

    @FXML
    private void goBack() {
        Stage stage = (Stage) viewEnrollmentsButton.getScene().getWindow();
        stage.close();  // Close the current window
    }
}
