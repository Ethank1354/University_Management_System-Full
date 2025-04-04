package engg1420_project.universitymanagementsystem.events;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditEventController {

    @FXML
    private ComboBox<String> eventComboBox;

    @FXML
    private TextField eventNameField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField costField;

    // Method to handle event editing
    @FXML
    public void editEvent(ActionEvent event) {
        String selectedEvent = eventComboBox.getValue();
        if (selectedEvent == null || selectedEvent.isEmpty()) {
            showAlert("Error", "Please select an event to edit!", AlertType.ERROR);
            return;
        }

        // Event editing logic can go here (e.g., modify event details)
        showAlert("Success", "Event Edited Successfully!", AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
