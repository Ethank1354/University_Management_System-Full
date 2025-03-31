package engg1420_project.universitymanagementsystem.login;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.faculty.facultyController;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.student.StdDashCtrl;
import engg1420_project.universitymanagementsystem.student.StdProfileViewCtrl;
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
import javafx.scene.layout.Pane;
import javafx.stage.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginController {
    @FXML
    public Label welcomeText, fail;
    @FXML
    public TextField email, id;
    @FXML
    public Button student_management, event_management, subject_management, faculty_management, course_management, main, dashboard, login;
    @FXML
    public AnchorPane contentPane;
    @FXML
    public Pane pane;
    @FXML
    public Button profile_management;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String loginUser;
    private DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    //User Info
    private String[] studentID;

    private String[] studentEmail = {"alice@example.edu", "bob.@example.edu", "carol@example.edu", "lucka@example.edu", "lee@example.edu", "brown@example.edu", "smith@example.edu", "jones@example.edu", "clarka@example.edu", "davis@example.edu"};

    private String[] facultyID = {"F0001", "F0002", "F0003", "F0004", "F0005"};
    private String[] facultyEmail = {"turing@university.edu", "bronte@university.edu", "hopper@university.edu", "copeland@university.edu", "gharabaghi@university.edu"};
    private String[] facultyOffice = {"Room 201", "Room 202", "Lab 203", "Room 201", "Lab 202"};

    private String adminID = "admin123";
    private String adminEmail = "admin@uni";

    public LoginController() {
        try {
            studentID =  db.getColumnValues("Students", "StudentID").toArray(new String[0]);  //studentIDs.toArray(new String[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //private String[] studentID = {"S20250001", "S20250002", "S20250003", "S20250004", "S20250005", "S20250006", "S20250007", "S20250008", "S20250009", "S20250010"};


        // try {
            //studentID = (String[]) db.getColumnValues("Students", "Student ID").toArray();
       // } catch (SQLException e) {
        //    throw new RuntimeException(e);
        //}





    @FXML
    public void openDashboard(ActionEvent event) throws IOException {
        if (checkLogin()) {
            try{
                if(loginUser.equals("Admin")) {
                    Parent dashboardFX = FXMLLoader.load(HelloApplication.class.getResource("dashboard.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(dashboardFX);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    Parent dashboardFX = FXMLLoader.load(HelloApplication.class.getResource("menuUser.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(dashboardFX);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Load the AdminView.fxml into the right side of the screen
//            Parent dashboardFX = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menuUser.fxml")));
//            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            scene = new Scene(dashboardFX);
//            stage.setScene(scene);
//            stage.show();
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
        if(email.getText().toString().equals(adminEmail) && id.getText().toString().equals(adminID)){
            loginUser = "Admin";
            return true;
        }
        else {
            return false;
        }
    }
    @FXML
    void openDashboardMain(ActionEvent event) throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            if(loginUser.equals("Admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboardAdmin.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            }
            else if(loginUser.equals("Faculty")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashboardFaculty.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashboardStudent.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openFaculty() throws IOException {
        try {
            if(loginUser.equals("Admin")) {
                //Load the FXML and set the controller
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-overview.fxml"));
                facultyController facultycontroller = new facultyController(db, "admin", contentPane);
                fxmlLoader.setController(facultycontroller);
                AnchorPane pane = fxmlLoader.load();

                // Set the right-side content to the new pane
                contentPane.getChildren().setAll(pane);
            }else{
                if(loginUser.equals("Faculty")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
                    facultyController facultycontroller = new facultyController(db, id.getText(), contentPane);
                    fxmlLoader.setController(facultycontroller);
                    AnchorPane pane = fxmlLoader.load();
                }else if(loginUser.equals("Student")) {

                }else{

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /*@FXML
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
    }*/


    @FXML
    void openStudents() throws IOException, SQLException {



        try {
            if (loginUser.equals("Admin")) {
                // Load the FXML and set the controller
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student/StdDashboard.fxml"));
                StdDashCtrl controller = new StdDashCtrl(db, loginUser);
                fxmlLoader.setController(controller);
                AnchorPane pane = fxmlLoader.load();

                // Set the right-side content to the new pane
                contentPane.getChildren().setAll(pane);
            } else if (loginUser.equals("Faculty")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student/StdDashboard.fxml"));
                StdDashCtrl controller = new StdDashCtrl(db, loginUser);
                fxmlLoader.setController(controller);
                AnchorPane pane = fxmlLoader.load();

            }

            } catch(IOException e){
                e.printStackTrace();
            } catch(SQLException e){
                throw new RuntimeException(e);
            }


//
    }

//    @FXML
//    void openCourses(ActionEvent event) throws IOException {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminView.fxml"));
//
//            Parent root = fxmlLoader.load();
//
//            Stage newStage = new Stage();
//            newStage.setScene(new Scene(root, 600, 400));
//            newStage.setTitle("Student Dashboard");
//            newStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    void openCourses(ActionEvent event) throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            if(loginUser.equals("Admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("course/ViewCoursesAdmin.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            }
            else if(loginUser.equals("Faculty")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("course/ViewFaculty.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("course/ViewStudent.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
//        try {
//            // Load the AdminView.fxml into the right side of the screen
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminView.fxml"));
//            AnchorPane pane = fxmlLoader.load();
//
//            // Replace the content of contentPane (the right side)
//            contentPane.getChildren().setAll(pane);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


//    @FXML
//    void openEvents(ActionEvent event) throws IOException {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
//
//            Parent root = fxmlLoader.load();
//
//            Stage newStage = new Stage();
//            newStage.setScene(new Scene(root, 600, 400));
//            newStage.setTitle("Student Dashboard");
//            newStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    void openEvents(ActionEvent event) throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("events/EventManagement.fxml"));

            AnchorPane pane = fxmlLoader.load();

            // Replace the content of contentPane (the right side)
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openSubjects(ActionEvent event) throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("subject/admin-view-subjects.fxml"));
            AnchorPane pane = fxmlLoader.load();

            // Replace the content of contentPane (the right side)
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openProfile(ActionEvent actionEvent) {
        try {
            // Load the AdminView.fxml into the right side of the screen
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
            AnchorPane pane = fxmlLoader.load();

            // Replace the content of contentPane (the right side)
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}