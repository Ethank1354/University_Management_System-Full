package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class DeleteEventController {

    @FXML
    private TextField eventCodeField;

    @FXML
    private void deleteEvent() {
        String eventCode = eventCodeField.getText();

        // Logic to remove event from list or database
        showAlert("Event Deleted", "The event '" + eventCode + "' has been deleted.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
