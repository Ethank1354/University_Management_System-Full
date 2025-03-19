package engg1420_project.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;

import java.io.IOException;
import java.util.Objects;

public class DashboardAdmin {

    @FXML
    public AnchorPane contentPane;
/*
    public void openDashboardMain(ActionEvent event) throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            FXMLLoader fxmlLoader;
            if(LoginController.loginUser.equals("Admin")){
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashBoardAdmin.fxml"));
            }
            else if(LoginController.loginUser.equals("Faculty")) {
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashBoardFaculty.fxml"));
            }
            else {
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboardStudentMain.fxml"));
            }
            AnchorPane pane = fxmlLoader.load();


            // Replace the content of dashboard_main (the right side)
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   */
}
