package engg1420_project.universitymanagementsystem;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.HashMap;


public class StdProfileViewCtrl  {



   private sharedModel sm = new sharedModel();
   // private String target;

    @FXML
    private ListView<String> courseListView;


    @FXML
    private Label label_studentName, label_studentid, label_address, label_phone, label_email;

    @FXML
    public void initialize() {

      //  courseListView.getItems().addAll("ENGG 1500", "ENGG 1420", "ENGG 1210", "MATH 1210", "PHYS 1010");
       // initData(sm, sharedModel.getSelectedName());

        //Reads what cell is selected and sets it as a string
        String target = sharedModel.getSelectedName();

        //Accesses the student object connected to the key string
        //Sets all the labels to fill the main information
        label_studentName.setText(sm.getValueForKey(target).getName());
        //label_studentid.setText(studentHashMap.get(target).getId());
        label_address.setText(sm.getValueForKey(target).getAddress());
       // label_phone.setText(studentHashMap.get(target).getPhone());
        label_email.setText(sm.getValueForKey(target).getEmail());



    }

/*
    public void initData(sharedModel dataStorage, String studentKey) {
        dataStorage = sm;
        studentKey = target;

        // Initialize the fields with the current values
        Student std = dataStorage.getValueForKey(studentKey);

        label_studentName.setText(std.getName());
        //label_studentid.setText(std.getId());
        label_address.setText(std.getAddress());
        // label_phone.setText(std.getPhone());
        label_email.setText(std.getEmail());

    }

 */
    //Named weird but just the exit button
    @FXML
    void management(ActionEvent event) throws IOException {
        try {
            // Load the FXML for the Faculty-Profile.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(StdDashApp.class.getResource("StdDashboard.fxml"));

            // Load the scene from the FXML file
            Parent root = fxmlLoader.load();

            // Create a new stage (window)
            Stage newStage = new Stage();

            // Create a new scene and set it for the new stage
            Scene scene = new Scene(root, 600, 400); // Adjust width and height as needed
            newStage.setTitle("Student Information");

            // Set the scene to the new stage and show it
            newStage.setScene(scene);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
