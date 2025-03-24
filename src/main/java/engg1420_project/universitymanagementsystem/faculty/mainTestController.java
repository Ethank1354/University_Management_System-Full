package engg1420_project.universitymanagementsystem.faculty;


import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class mainTestController {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private VBox mainVBox;

    @FXML
    void initialize() throws SQLException, IOException {

        DatabaseManager db = new DatabaseManager("/home/user/test.db");
        String access;

        String username = "admin";  //"F0001" ""S20250001";
        if(username.equals("admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-overview.fxml"));
            fxmlLoader.setController(new facultyController(db, "admin", mainAnchorPane));
            AnchorPane pane = fxmlLoader.load();
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(pane);
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
            if(db.belongsToTable("Faculties", username)){
                access = "faculty";
            }else if(db.belongsToTable("Students", username)){
                access = "student";
            }else{
                access = "student";
            }
            if(db.belongsToTable("Faculties", username)) {
                fxmlLoader.setController(new FacultyProfileController(username, access, db, mainAnchorPane));
            }else{
                fxmlLoader.setController(new FacultyProfileController("F0001", access, db, mainAnchorPane));
                AnchorPane pane = fxmlLoader.load();
                mainAnchorPane.getChildren().clear();
                mainAnchorPane.getChildren().add(pane);
            }

        }

    }

}
