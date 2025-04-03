package engg1420_project.universitymanagementsystem.events;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ViewEventsController {

    @FXML
    private ComboBox<String> eventComboBox;

    @FXML
    private Label eventDetailsLabel;

    // Method to handle event viewing
    @FXML
    public void viewEvent(ActionEvent event) {
        String selectedEvent = eventComboBox.getValue();
        if (selectedEvent == null || selectedEvent.isEmpty()) {
            eventDetailsLabel.setText("Please select an event to view!");
            return;
        }

        // Fetch event details and display them (this is just a dummy example)
        eventDetailsLabel.setText("Displaying details for: " + selectedEvent);
    }
}
