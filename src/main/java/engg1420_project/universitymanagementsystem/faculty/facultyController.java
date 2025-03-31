package engg1420_project.universitymanagementsystem.faculty;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.HelloApplication;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class facultyController {
    private DatabaseManager db;
    private String username;
    private String access;
    private Faculty faculty;
    private AnchorPane superAnchorPane;
    private VBox superVBox;

    public facultyController(DatabaseManager db, String username, AnchorPane mainAnchorPane) throws SQLException {
        this.db = db;
        this.username = username;
        this.superAnchorPane = mainAnchorPane;

        if (username.equalsIgnoreCase("admin")) {
            this.access = "admin";
        }else if(db.belongsToTable("Faculties", username)){
            this.access = "faculty";
        }else if(db.belongsToTable("Students", username)){
            this.access = "student";
        }else{
            this.access = "student";
        }
    }

    @FXML
    private ListView<String> facultyList;

    @FXML
    private Button addFacultyButton;

    @FXML
    public void initialize() throws SQLException {
        List<String> viewableInfo = new ArrayList<>();
        List<String> facultyManes = db.getColumnValues("Faculties", "Name");
        List<String> facultyIDs = db.getColumnValues("Faculties", "Faculty ID");

        for(int i = 0; i < facultyIDs.size(); i++){
            viewableInfo.add(facultyIDs.get(i) + ":" + facultyManes.get(i));
        }
        for(int i = 0; i < viewableInfo.size(); i++){
            System.out.println(viewableInfo.get(i));
        }


        facultyList.getItems().addAll(viewableInfo);

        facultyList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem viewProfile = new MenuItem();
            viewProfile.textProperty().bind(Bindings.format("View Profile for \"%s\"", cell.itemProperty()));
            viewProfile.setOnAction(event -> openViewProfile(cell.getItem()));

            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> deleteFaculty(cell.getItem()));

            MenuItem assignCourses = new MenuItem();
            assignCourses.textProperty().bind(Bindings.format("Assign Courses to \"%s\"", cell.itemProperty()));
            assignCourses.setOnAction(event -> {assignCourses(cell.getItem());
            });

            contextMenu.getItems().addAll(viewProfile, deleteItem, assignCourses);
            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });


            if(access.equals("student") || access.equals("faculty")){
                deleteItem.setDisable(true);
                assignCourses.setDisable(true);
            }

            return cell;
        });

        // Hide button when checkbox is unchecked
        if(access.equals("student") || access.equals("faculty")){
            addFacultyButton.setVisible(false);
        }
        //addFacultyButton.visibleProperty().bind(accessCheck.selectedProperty());

    }

    @FXML
    void addFaculty(ActionEvent event) throws IOException {
        try{
            Stage currentStage = (Stage) facultyList.getScene().getWindow();
            Scene previousScene = currentStage.getScene(); // Save current scene

            addFacultyController addFacultyController = new addFacultyController(previousScene, db, superAnchorPane);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/add-faculty.fxml"));
            fxmlLoader.setController(addFacultyController);
            AnchorPane pane = fxmlLoader.load();

            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void deleteFaculty(String facultyInfo) {
        facultyList.getItems().remove(facultyInfo);
        String[] parts = facultyInfo.split(":");
        System.out.println(parts[0]);
        try {
            db.deleteRowFromTable("Faculties", "Faculty ID", parts[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void openViewProfile(String facultyInfo) {

        try{
            // Get current stage and store previous scene
            Stage currentStage = (Stage) facultyList.getScene().getWindow();
            Scene previousScene = currentStage.getScene(); // Save current scene

            FacultyProfileController profileController = new FacultyProfileController(facultyInfo, access, db, superAnchorPane);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
            fxmlLoader.setController(profileController);

            AnchorPane pane = fxmlLoader.load();

            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void assignCourses(String facultyInfo) {

        try{
            String[] parts = facultyInfo.split(":");
            Stage currentStage = (Stage) facultyList.getScene().getWindow();
            Scene previousScene = currentStage.getScene(); // Save current scene

            assignCoursesController assignCoursesController = new assignCoursesController(db, previousScene, parts[0], superAnchorPane);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/assign-courses.fxml"));
            fxmlLoader.setController(assignCoursesController);
            AnchorPane pane = fxmlLoader.load();

            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }





    }


}