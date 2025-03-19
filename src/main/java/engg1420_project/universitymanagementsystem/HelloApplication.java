//package com.example.engg1420facultymanagement;
package engg1420_project.universitymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DatabaseManager db = new DatabaseManager("/home/user/test.db");
        String access;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("test-main.fxml"));
        fxmlLoader.setController(new mainTestController());
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Hello World");
        stage.show();

        /*List<String> faculty = db.getColumnValues("Students", "Student ID");
        for (String facultyName : faculty) {
            System.out.println("Faculty: " + facultyName);
        }*/
        /*String username = "admin";  //"F0001" ""S20250001";
        if(username.equals("admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-overview.fxml"));
            fxmlLoader.setController(new facultyController(db, username));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Faculty Overview");
            stage.setScene(scene);
            stage.show();
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-profile.fxml"));
            if(db.belongsToTable("Faculties", username)){
                access = "faculty";
            }else if(db.belongsToTable("Students", username)){
                access = "student";
            }else{
                access = "student";
            }
            if(db.belongsToTable("Faculties", username)) {
                fxmlLoader.setController(new FacultyProfileController(username, access, db));
            }else{
                fxmlLoader.setController(new FacultyProfileController("F0001", access, db));

            }
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Faculty Profile");
            stage.setScene(scene);
            stage.show();
        }*/

    }

    public static void main(String[] args) {
        launch();
    }
}