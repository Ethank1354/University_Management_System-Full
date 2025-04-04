package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class EventManagementController {

    @FXML
    private VBox eventList; // Link this to your FXML

    private int eventCount = 2; // Start from EV003

    @FXML
    private void addEvent() {
        eventCount++;

        // Get user input for event details
        String eventName = getInput("Add Event", "Enter Event Name:");
        if (eventName == null) return;

        String description = getInput("Add Event", "Enter Description:");
        if (description == null) return;

        String location = getInput("Add Event", "Enter Location:");
        if (location == null) return;

        String dateTime = getInput("Add Event", "Enter Date and Time:");
        if (dateTime == null) return;

        String capacity = getInput("Add Event", "Enter Capacity:");
        if (capacity == null || !capacity.matches("\\d+")) {
            showAlert("Invalid Input", "Capacity must be a number.");
            return;
        }

        String cost = getInput("Add Event", "Enter Cost (e.g., Free or $20):");
        if (cost == null) return;

        // Create new event
        VBox newEvent = new VBox();
        newEvent.setSpacing(5);
        newEvent.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 5; -fx-border-color: lightgray;");

        Label eventCodeLabel = new Label("EV" + String.format("%03d", eventCount));
        eventCodeLabel.setStyle("-fx-font-weight: bold;");
        Label eventNameLabel = new Label("Event Name: " + eventName);
        Label eventDescLabel = new Label("Description: " + description);
        Label eventLocationLabel = new Label("Location: " + location);
        Label eventDateLabel = new Label("Date and Time: " + dateTime);
        Label eventCapacityLabel = new Label("Capacity: " + capacity);
        Label eventCostLabel = new Label("Cost: " + cost);

        newEvent.getChildren().addAll(eventCodeLabel, eventNameLabel, eventDescLabel, eventLocationLabel, eventDateLabel, eventCapacityLabel, eventCostLabel);

        // Add event to the list
        if (eventList != null) {
            eventList.getChildren().add(newEvent);
        } else {
            showAlert("Error", "Event list container not found.");
        }
    }

    @FXML
    private void deleteEvent() {
        if (eventList != null && !eventList.getChildren().isEmpty()) {
            eventList.getChildren().remove(eventList.getChildren().size() - 1);
            eventCount--;
        } else {
            showAlert("Delete Event", "No events to delete.");
        }
    }

    @FXML
    private void editEvent() {
        if (eventList != null && !eventList.getChildren().isEmpty()) {
            VBox lastEvent = (VBox) eventList.getChildren().get(eventList.getChildren().size() - 1);

            String newName = getInput("Edit Event", "Enter new event name:");
            if (newName != null) {
                ((Label) lastEvent.getChildren().get(1)).setText("Event Name: " + newName);
            }
        } else {
            showAlert("Edit Event", "No events available to edit.");
        }
    }

    @FXML
    private void viewEvents() {
        if (eventList != null && !eventList.getChildren().isEmpty()) {
            StringBuilder eventDetails = new StringBuilder("Event List:\n");
            for (var eventNode : eventList.getChildren()) {
                VBox event = (VBox) eventNode;
                eventDetails.append(((Label) event.getChildren().get(0)).getText()).append("\n");
            }
            showAlert("View Events", eventDetails.toString());
        } else {
            showAlert("View Events", "No events available.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getInput(String title, String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
