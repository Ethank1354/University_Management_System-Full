//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem.student;


import engg1420_project.universitymanagementsystem.*;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class StdDashCtrl {

    private DatabaseManager db;
    private String username;
    private String access;
    private Student student;
    private String ID;

    //Controller Constructor
    public StdDashCtrl(DatabaseManager db, String username) throws SQLException {
        this.db = db;
        this.username = username;
    }

    public StdDashCtrl(DatabaseManager db, String access, String ID) throws SQLException {
        this.db = db;
        this.access = access;
        this.ID = ID;
    }



    @FXML
    private ListView<String> listViewStudent;

    @FXML
    private Button btnView;

    @FXML
    private Button btnDelStd;

    @FXML
    private Button btnAddStd;

    @FXML
    private AnchorPane contentPane;

    //Buttons:
    @FXML
    public void deleteStd(ActionEvent event) throws IOException {
        listViewStudent.getItems().remove(sharedDatabase.getSelectedName());
        String[] parts = sharedDatabase.getSelectedName().split(":");
        System.out.println(parts[0]);
        try {
            db.deleteRowFromTable("Students", "Student ID", parts[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void viewStd(ActionEvent event) throws IOException {
        try {
            // Load the FXML and set the controller
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student/StdViewProfile.fxml"));
            StdProfileViewCtrl profileController = new StdProfileViewCtrl(db, access, sharedDatabase.getSelectedName());
            fxmlLoader.setController(profileController);
            AnchorPane pane = fxmlLoader.load();

            // Set the right-side content to the new pane
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void addStd(ActionEvent event) throws IOException {
        try {
            // Load the FXML and set the controller
            StdCreateCtrl profileController = new StdCreateCtrl(db, username);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/StdProfileAdd.fxml"));
            fxmlLoader.setController(profileController);
            AnchorPane pane = fxmlLoader.load();

            // Set the right-side content to the new pane
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() throws SQLException {
        //Disabling Buttons for Faculty
        /*
        if (access == "Faculty") {
            btnAddStd.setDisable(true);
            btnDelStd.setDisable(true);
        } else {
            btnAddStd.setDisable(false);
            btnDelStd.setDisable(false);
        }
         */
        //Populating the List view with the student names and student IDs
        List<String> viewableInfo = new ArrayList<>();
        List<String> StudentNames = db.getColumnValues("Students", "Name");
        List<String> StudentIDs = db.getColumnValues("Students", "Student ID");

        for(int i = 0; i < StudentIDs.size(); i++){
            viewableInfo.add(StudentIDs.get(i) + ":" + StudentNames.get(i));
        }
        for(int i = 0; i < viewableInfo.size(); i++){
            System.out.println(viewableInfo.get(i));
        }
        listViewStudent.getItems().addAll(viewableInfo);

        //Creating a listener that tracks which list cell is selected
        listViewStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            sharedDatabase.setSelectedName(newValue);  // Save the selected name to SharedModel
        });



        //Creates the right click menu
        listViewStudent.setCellFactory(lv -> {

            ListCell<String> cell = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();

            //Creating the view option for the right click menu
            MenuItem viewProfile = new MenuItem();
            viewProfile.textProperty().bind(Bindings.format("View Profile for \"%s\"", cell.itemProperty()));
            viewProfile.setOnAction(event -> {
                String item = cell.getItem();
                try {
                    StdProfileViewCtrl profileController = new StdProfileViewCtrl(db, access, item);

                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/StdViewProfile.fxml"));
                    fxmlLoader.setController(profileController);

                    AnchorPane pane = fxmlLoader.load();

                    contentPane.getChildren().setAll(pane);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            //Creating the edit profile option for the right-click menu
            MenuItem editProfile = new MenuItem();
            editProfile.textProperty().bind(Bindings.format("Edit Profile for \"%s\"", cell.itemProperty()));
            editProfile.setOnAction(event -> {
                String item = cell.getItem();

                try{
                    Stage currentStage = (Stage) listViewStudent.getScene().getWindow();
                    Scene previousScene = currentStage.getScene(); // Save current scene

                    StdProfileEditCtrl stdProfileEditCtrl = new StdProfileEditCtrl(db, item, username);
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/StdProfileEditing.fxml"));
                    fxmlLoader.setController(stdProfileEditCtrl);

                    AnchorPane pane = fxmlLoader.load();
                    contentPane.getChildren().setAll(pane);

                } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            });

            //Creates the delete option for the right click menu
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                String item = cell.getItem();
                listViewStudent.getItems().remove(item);
                String[] parts = item.split(":");
                System.out.println(parts[0]);
                try {
                    db.deleteRowFromTable("Students", "Student ID", parts[0]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            //Adding all the options to the click down menu
            contextMenu.getItems().addAll(viewProfile, deleteItem, editProfile);

            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });



            return cell;
            });
    }
}






