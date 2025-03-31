package engg1420_project.universitymanagementsystem.events;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class DeleteEventController {

    @FXML
    private TextField eventCodeField;

    @FXML
    private VBox eventList; // Reference to the VBox holding events

    @FXML
    private void deleteEvent() {
        String eventIdToDelete = eventCodeField.getText().trim();

        if (eventIdToDelete.isEmpty()) {
            showAlert("Error", "Please enter an event ID.");
            return;
        }

        Node eventToRemove = null;
        for (Node node : eventList.getChildren()) {
            if (node instanceof VBox eventBox) {
                Label idLabel = (Label) eventBox.getChildren().get(0);
                if (idLabel.getText().equals(eventIdToDelete)) {
                    eventToRemove = eventBox;
                    break;
                }
            }
        }

        if (eventToRemove != null) {
            eventList.getChildren().remove(eventToRemove);
            showAlert("Success", "Event " + eventIdToDelete + " has been deleted.");
        } else {
            showAlert("Error", "Event ID not found.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
