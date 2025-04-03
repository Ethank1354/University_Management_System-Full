package engg1420_project.universitymanagementsystem;

import engg1420_project.universitymanagementsystem.events.DeleteEventController;
import engg1420_project.universitymanagementsystem.events.EditEventController;
import engg1420_project.universitymanagementsystem.events.ViewEventsController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AdminController {

    @FXML
    private TextField eventNameField;
    @FXML
    private TextField eventCodeField;
    @FXML
    private TextField eventDescriptionField;
    @FXML
    private ImageView headerImageView;

    // Add Event
    @FXML
    private void addEvent() {
        String eventName = eventNameField.getText();
        String eventCode = eventCodeField.getText();
        String eventDescription = eventDescriptionField.getText();

        Event newEvent = new Event(eventName, eventCode, eventDescription, "default.jpg", "Location", "2025-03-20 10:00", 100, 0.0);
        // Save the new event in a list or database

        showAlert("Event Added", "The event was successfully added.");
    }

    // Edit Event
    @FXML
    private void editEvent() {
        new EditEventController();
    }

    // Delete Event
    @FXML
    private void deleteEvent() {
        new DeleteEventController();
    }

    // View Events
    @FXML
    private void viewEvents() {
        new ViewEventsController();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
