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