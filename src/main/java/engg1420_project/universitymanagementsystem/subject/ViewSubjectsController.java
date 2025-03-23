package engg1420_project.universitymanagementsystem.subject;

import engg1420_project.universitymanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class ViewSubjectsController {

    @FXML
    void viewSubjects(ActionEvent event)  throws IOException {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-subjects.fxml"));

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

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<EditSubjectsController> subjectsTable;
/*
    // Custom initialization method to be called from the Main class after the FXML is loaded.
    public void initializeData() {
        // Set welcome message
        welcomeLabel.setText("Welcome, Admin!");

        // Example: Populate a TableView with sample data
        ObservableList<EditSubjectsController> userList = FXCollections.observableArrayList();
        userList.add(new EditSubjectsController("John Doe", "Administrator"));
        userList.add(new EditSubjectsController("Jane Smith", "Moderator"));
        TableView<EditSubjectsController> userTable;
        userTable.setItems(userList);
    }
 */
}
