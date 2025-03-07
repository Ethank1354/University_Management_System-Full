package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.scene.Node;

public class DeleteSubjectsController {

    @FXML
    void viewSubjects(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("delete-subjects.fxml"));

            // Load the new FXML file
            Parent newRoot = fxmlLoader.load();

            // Get the current stage using the event's source node
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML
            Scene newScene = new Scene(newRoot, 600, 400);
            stage.setTitle("Subject Information");

            // Set the new scene on the stage and display it
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally: display an alert or handle the error appropriately
        }

    }

}
