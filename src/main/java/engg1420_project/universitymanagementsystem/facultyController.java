package engg1420_project.universitymanagementsystem;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class facultyController {
    //DatabaseManager db;


    @FXML
    private ListView<String> facultyList;

    @FXML
    private CheckBox accessCheck;

    @FXML
    private Button addFacultyButton;

    @FXML
    public void initialize()  {

        facultyList.getItems().addAll("one", "two", "three", "four", "five");

        facultyList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem viewProfile = new MenuItem();
            viewProfile.textProperty().bind(Bindings.format("View Profile for \"%s\"", cell.itemProperty()));
            viewProfile.setOnAction(event -> openViewProfile(cell.getItem(), accessCheck.isSelected()));

            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> facultyList.getItems().remove(cell.getItem()));

            MenuItem assignCourses = new MenuItem();
            assignCourses.textProperty().bind(Bindings.format("Assign Courses to \"%s\"", cell.itemProperty()));
            assignCourses.setOnAction(event -> openAssignCourses(cell.getItem()));

            contextMenu.getItems().addAll(viewProfile, deleteItem, assignCourses);
            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            // Disable delete and assign courses based on checkbox
            accessCheck.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                deleteItem.setDisable(!isSelected);
                assignCourses.setDisable(!isSelected);
            });

            return cell;
        });

        // Hide button when checkbox is unchecked
        addFacultyButton.visibleProperty().bind(accessCheck.selectedProperty());
    }

    @FXML
    void addFaculty(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-faculty.fxml"));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            newStage.setTitle("Add Faculty");
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openViewProfile(String facultyName, boolean hasAccess) {
        try {
            FacultyProfileController profileController = new FacultyProfileController(hasAccess);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-profile.fxml"));
            fxmlLoader.setController(profileController);
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Faculty Profile");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAssignCourses(String facultyName) {
        openScene("assign-courses.fxml", "Assign Courses to " + facultyName);
    }

    private void openScene(String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle(title);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}