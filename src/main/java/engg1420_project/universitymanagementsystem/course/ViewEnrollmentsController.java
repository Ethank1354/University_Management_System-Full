//Mateo
package engg1420_project.universitymanagementsystem.course;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewEnrollmentsController {

    @FXML private Button viewEnrollmentsButton;
    @FXML private Button goBackButton;

    @FXML
    private void goBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();  // Close the current window
    }
}
