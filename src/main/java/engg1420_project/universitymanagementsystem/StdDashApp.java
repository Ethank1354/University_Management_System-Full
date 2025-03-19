//package engg1420group2.universitymanagementsystem.studentmanagement;
/*
package engg1420_project.universitymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;

public class StdDashApp extends Application {
//    DatabaseManager db = new DatabaseManager("C:/Users/egank/Downloads/UMdatabase.sqlite");

    //Determining the type of user, admin or other
    String permission = "Admin";
    String username = "";


    @Override
    public void start(Stage stage) throws IOException, SQLException {

        if (permission.equals("Admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(StdDashApp.class.getResource("StdDashboard.fxml"));
            fxmlLoader.setController(new StdDashCtrl(db, username));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Student Management System (Admin View)");
            stage.setScene(scene);
            stage.show();

        } else if (username == null) {
            permission = "Faculty";
            FXMLLoader fxmlLoader = new FXMLLoader(StdDashApp.class.getResource("StdDashboard.fxml"));
            fxmlLoader.setController(new StdDashCtrl(db, username));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Student Management System");
            stage.setScene(scene);
            stage.show();

        } else {
            permission = "Student";
            FXMLLoader fxmlLoader = new FXMLLoader(StdDashApp.class.getResource("StdViewProfile.fxml"));
            fxmlLoader.setController(new StdDashCtrl(db, username));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Student Management System");
            stage.setScene(scene);
            stage.show();

        }


    }

    public static void main(String[] args) {launch(); }
}

 */