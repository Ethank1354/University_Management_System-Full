package engg1420_project.universitymanagementsystem;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class FacultyProfileController {
    private boolean buttonClicked;

    public FacultyProfileController(boolean buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    @FXML
    private Button editButton;

    @FXML
    private ListView<String> coursesListView;

    @FXML
    public void initialize() {
        // Set the visibility of the edit button based on buttonClicked
        editButton.setVisible(buttonClicked);

        coursesListView.getItems().addAll("One", "Two", "Three");

        coursesListView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu coursesMenu = new ContextMenu();

            MenuItem viewStudents = new MenuItem();
            viewStudents.textProperty().bind(Bindings.format("View students in \"%s\"", cell.itemProperty()));
            viewStudents.setOnAction(event -> openStudentsList());

            // Disable the menu item if buttonClicked is false
            viewStudents.setDisable(!buttonClicked);

            coursesMenu.getItems().add(viewStudents);

            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(coursesMenu);
                }
            });

            return cell;
        });
    }

    private void openStudentsList() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("students-list.fxml"));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Students List");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}