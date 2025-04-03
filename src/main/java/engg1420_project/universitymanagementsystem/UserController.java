package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class UserController {
    private String userId;
    private String name;
    private String email;
    private boolean isAdmin;  // To distinguish between Admin and User

    public UserController(String userId, String name, String email, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @FXML
    private ListView<String> eventListView;

    // Register for event
    @FXML
    private void registerForEvent() {
        String selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            // Logic to register user for the selected event
            showAlert("Registration Successful", "You have been registered for the event.");
        } else {
            showAlert("Error", "Please select an event to register.");
        }
    }

    // View Registered Events
    @FXML
    private void viewRegisteredEvents() {
        // Logic to view registered events
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

