package engg1420_project.universitymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent loginFX = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = new Stage();
        Scene scene = new Scene(loginFX, 320, 240);
        stage.setTitle("University Management System!");

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}


//package com.example.project;
//package engg1420_project.universitymanagementsystem;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class Main extends Application {
//
//    public static String user = "Admin"; // Manual login placeholder for testing
//
//    private Stage primaryStage;
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        this.primaryStage = primaryStage;
//        switchToView();
//    }
//
//    private void switchToView() throws IOException {
//        String fxmlFile = "";
//
//        if ("Admin".equals(user)) {
//            fxmlFile = "AdminView.fxml";
//        } else if ("Faculty".equals(user)) {
//            fxmlFile = "FacultyView.fxml";
//        } else if ("Student".equals(user)) {
//            fxmlFile = "StudentView.fxml";
//        }
//        //ViewCoursesAdminController.fxml
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/" + fxmlFile));
//        Parent root = loader.load();
//
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle(user + " View");
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}