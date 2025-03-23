
package engg1420_project.universitymanagementsystem.login;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("University Management System - Dashboard");

        // Layout and UI elements
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
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

        //Left side menu
        Button dashboard = new Button("Dashboard");
        HBox hbDash = new HBox(10);
        hbDash.setAlignment(Pos.TOP_LEFT);
        hbDash.getChildren().add(dashboard);
        grid.add(hbDash, 1, 4);

        Button student_management = new Button("Student Management");
        HBox hbStudent = new HBox(10);
        hbStudent.setAlignment(Pos.TOP_LEFT);
        hbStudent.getChildren().add(student_management);
        grid.add(hbStudent, 1, 5);

        Button event_management = new Button("Event Management");
        HBox hbEvent = new HBox(10);
        hbEvent.setAlignment(Pos.TOP_LEFT);
        hbEvent.getChildren().add(student_management);
        grid.add(hbEvent, 1, 6);

        Button course_management = new Button("Course Management");
        HBox hbBtnCM = new HBox(10);
        hbBtnCM.setAlignment(Pos.TOP_LEFT);
        hbBtnCM.getChildren().add(student_management);
        grid.add(hbBtnCM, 1, 7);

        Button subject_management = new Button("Subject Management");
        HBox hbSubject = new HBox(10);
        hbSubject.setAlignment(Pos.TOP_LEFT);
        hbSubject.getChildren().add(student_management);
        grid.add(hbSubject, 1, 5);

        Text loginStatus = new Text();
        grid.add(loginStatus, 1, 6);

        // Login button action
        dashboard.setOnAction(e -> {
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

