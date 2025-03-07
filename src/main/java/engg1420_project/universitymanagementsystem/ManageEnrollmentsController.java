package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ManageEnrollmentsController {

    @FXML private Button manageEnrollmentsButton; // Declare the button

    @FXML
    private void goBack() {
        Stage stage = (Stage) manageEnrollmentsButton.getScene().getWindow();
        stage.close();  // Close the current window
    }
}
