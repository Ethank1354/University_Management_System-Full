package engg1420_project.universitymanagementsystem.subject;

import engg1420_project.universitymanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;


public class AdminViewControllerSubjects {

    private Stage stage;
    private Scene scene;

public void setStage(Stage stage) {
    this.stage = stage;
}

    @FXML
    void determineRole() {

    Scanner scanner = new Scanner(System.in);
    System.out.print("What role are you (Student, admin, or faculty): ");
    String role = scanner.nextLine();

    if (role.equalsIgnoreCase("Admin")) {
        switchToView("subject/admin-view.fxml");
    } else if (role.equalsIgnoreCase("Student") || role.equalsIgnoreCase("faculty")) {
        switchToView("subject/user-view.fxml");
        } else {
        System.exit(0);
    }
    }

    @FXML
    private void switchToView(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/project/" + fxmlFile));

            // Load the new FXML file
            Parent newRoot = fxmlLoader.load();

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
    void editSubjects() {

    }

    @FXML
    void viewSubjects() {

    }

    @FXML
    void createSubjects() {

    }

    @FXML
    void deleteSubjects() {

    }
    }
