package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class EventManagementController {

    @FXML
    private void addEvent() {
        showAlert("Add Event", "Feature to add an event will go here.");
    }

    @FXML
    private void editEvent() {
        showAlert("Edit Event", "Feature to edit an event will go here.");
    }

    @FXML
    private void deleteEvent() {
        showAlert("Delete Event", "Feature to delete an event will go here.");
    }

    @FXML
    private void viewEvents() {
        showAlert("View Events", "Feature to view events will go here.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
