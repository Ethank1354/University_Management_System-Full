package engg1420_project.universitymanagementsystem.events;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    @FXML
    private void onHelloButtonClick() {
        // Add function in a bit
    }

    @FXML
    private void openEventManagement() throws IOException {
        System.out.println("Event Management Clicked!");

        // Create a new Stage for Event Management
        Stage stage = new Stage();
        stage.setTitle("Event Management");

        // Loads the EventManagement.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteEventController.fxml"));
        AnchorPane root = loader.load();  // Load the FXML into the root layout

        // Creates a scene with the loaded root layout
        Scene scene = new Scene(root, 800, 600);

        // Set the scene and show the new window
        stage.setScene(scene);
        stage.show();
    }
}