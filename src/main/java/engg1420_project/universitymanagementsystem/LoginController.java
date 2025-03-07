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
import javafx.stage.*;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private Label welcomeText, fail;
    @FXML
    private TextField email, id;
    @FXML
    private Button student_Management, event_management, faculty_management, course_management, dashboard, login;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String loginUser;

    //User Info


    private String[] studentID = {"S20250001", "S20250002", "S20250003", "S20250004", "S20250005", "S20250006", "S20250007", "S20250008", "S20250009", "S20250010"};
    private String[] studentEmail = {"alice@example.edu", "bob.@example.edu", "carol@example.edu", "lucka@example.edu", "lee@example.edu", "brown@example.edu", "smith@example.edu", "jones@example.edu", "clarka@example.edu", "davis@example.edu"};

    private String[] facultyID = {"F0001", "F0002", "F0003", "F0004", "F0005"};
    private String[] facultyEmail = {"turing@university.edu", "bronte@university.edu", "hopper@university.edu", "copeland@university.edu", "gharabaghi@university.edu"};
    private String[] facultyOffice = {"Room 201", "Room 202", "Lab 203", "Room 201", "Lab 202"};

    @FXML
    public void openDashboard(ActionEvent event) throws IOException {
        if (checkLogin()) {
            Parent dashboardFX = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(dashboardFX);
            stage.setScene(scene);
            stage.show();
        }
        else{
            fail.setText("Incorrect. Try again.");
        }
    }
    public void openLogin(ActionEvent event) throws IOException {
        Parent loginFX = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(loginFX);
        stage.setScene(scene);
        stage.show();
    }

    public boolean checkLogin(){
        for(int i=0; i<facultyEmail.length; i++){
            if (email.getText().toString().equals(facultyEmail[i]) && id.getText().toString().equals(facultyID[i])){
                loginUser = "Faculty";
                return true;
            }
        }
        for(int i=0; i<studentEmail.length; i++){
            if (email.getText().toString().equals(studentEmail[i]) && id.getText().toString().equals(studentID[i])){
                loginUser = "Student";
                return true;
            }
        }
        return false;

    }

    @FXML
    void openFaculty(ActionEvent event) throws IOException {
        try {
            facultyController facultycontroller = new facultyController();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty-overview.fxml"));
            fxmlLoader.setController(facultycontroller);
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Faculty Overview");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openStudents(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StdDashboard.fxml"));

            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Student Dashboard");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openCourses(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminView.fxml"));

            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Student Dashboard");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openEvents(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));

            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Student Dashboard");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openSubjects(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view-events.fxml"));

            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Student Dashboard");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome our University Management System!");
//    }
//    protected void onUsername() {
//
//    }
}