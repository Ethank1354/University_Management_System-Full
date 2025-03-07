
package engg1420_project.universitymanagementsystem;//package engg1420_project.universitymanagementsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow extends Application {

//    private String[] students = {"Alice Smith", "Bob Johnson", "Carol Williams", "Kucka Rachl", "David Lee", "Emily Brown", "George Smith", "Helen Jones", "Isaac Clark", "Jennifer Davis"};

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("University Management System - Login");

        // Layout and UI elements
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userLabel = new Label("Username:");
        grid.add(userLabel, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pwLabel = new Label("Password:");
        grid.add(pwLabel, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        //Login Button
        Button loginButton = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        Text loginStatus = new Text();
        grid.add(loginStatus, 1, 6);

        // Login button action
        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            if (authenticate(username, password)) {
                loginStatus.setText("Login successful!");
                // Proceed to main system window (replace this with your main system logic)
                showMainDashboard(primaryStage);
            } else {
                loginStatus.setText("Invalid username or password.");
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Replace this with your actual authentication logic (e.g., database check)
    private boolean authenticate(String username, String password) {
        return username.equals("admin") && password.equals("password123"); // Example
    }

    // Placeholder for transitioning to the main dashboard window
    private void showMainDashboard(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to University Management System!");
        StackPane root = new StackPane(welcomeLabel);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

//    public String getStudents() {
//        return students;
//    }
//
//    public void setStudents(String students) {
//        this.students = students;
//    }
}

