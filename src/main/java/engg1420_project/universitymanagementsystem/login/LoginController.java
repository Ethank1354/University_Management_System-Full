package engg1420_project.universitymanagementsystem.login;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.faculty.FacultyProfileController;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
    @FXML
    public Label welcomeText, fail;
    @FXML
    public TextField email, id;
    @FXML
    public Button student_management, event_management, subject_management, faculty_management, profile_management, course_management, main, dashboard, login, logout;
    @FXML
    public AnchorPane contentPane, eventPane, facultyPane, coursePane, subjectPane, studentPane;
    @FXML
    public Pane pane;
    @FXML
    public GridPane gridPane;
    @FXML
    private ImageView logo;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String loginUser;
    private static String loginEmail;
    private DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    private String adminID = "admin123";
    private String adminEmail = "admin@uni";


    @FXML
    public void openDashboard(ActionEvent event) throws IOException, SQLException {
        if (checkLogin()) {
            try {
                if (loginUser.equals("Admin")) {
                    Parent dashboardFX = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("menu/dashboard.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(dashboardFX);
                    stage.setScene(scene);
                    stage.show();
                } else if (loginUser.equals("Student") || loginUser.equals("Faculty")) {
                    Parent dashboardFX = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("menu/menuUser.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(dashboardFX);
                    stage.setScene(scene);
                    stage.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fail.setText("Incorrect. Try again.");
        }
    }

    public void openLogin(ActionEvent event) throws IOException {
        Parent loginFX = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loginFX);
        stage.setScene(scene);
        stage.show();
    }

    public boolean checkLogin(){
        String[] column = {"Password"};
        // String[] filterColumn = {"Email"};
        //String[] studentColumn = {"Password"};

        List<String> facultyRow;
        List<String> studentRow;
        try{
            facultyRow = db.getFilteredValues("Faculties", column, "Email", email.getText().toString());
        }catch (SQLException e){
            facultyRow = new ArrayList<>();
            facultyRow.add("error");
        }

        try {
            studentRow = db.getFilteredValues("Students", column, "Email", email.getText().toString());
        }catch (SQLException e){
            studentRow = new ArrayList<>();
            studentRow.add("error");

        }
        facultyRow.add("error");
        studentRow.add("error");
//        System.out.println(email.getText() + " email without to string");
//        System.out.println(email.getText().toString() + " email with to string");
//        System.out.println(facultyRow.get(0));
//        System.out.println(studentRow);
        if(id.getText().toString().equals(facultyRow.get(0))){
            this.loginEmail = email.getText().toString();
            //System.out.println(loginID + "loginid should be set here");
            loginUser = "Faculty";
            return true;
        }else if(id.getText().toString().equals(studentRow.get(0))){
            loginUser = "Student";
            this.loginEmail = email.getText().toString();
            return true;
        }else if(email.getText().toString().equals(adminEmail) && id.getText().toString().equals(adminID)){
         loginUser = "Admin";
            return true;
        }
        else {
            return false;
        }
    }

    @FXML
    void openDashboardMain() throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            if (loginUser.equals("Admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard/dashboardAdmin.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            } else if (loginUser.equals("Faculty")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard/DashboardFaculty.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard/DashboardStudent.fxml"));
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
            if (loginUser.equals("Admin")) {
                //Load the FXML and set the controller
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-overview.fxml"));
                facultyController facultycontroller = new facultyController(db, "admin", contentPane);
                fxmlLoader.setController(facultycontroller);
                AnchorPane pane = fxmlLoader.load();

                // Set the right-side content to the new pane
                contentPane.getChildren().setAll(pane);
            } else if (loginUser.equals("Faculty")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
                facultyController facultycontroller = new facultyController(db, "admin", contentPane);
                fxmlLoader.setController(facultycontroller);
                AnchorPane pane = fxmlLoader.load();
//                } else if (loginUser.equals("Student")) {
//                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void openStudents() throws IOException {
        try {
            // Load the FXML and set the controller
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student/StdDashboard.fxml"));

            StdDashCtrl controller = new StdDashCtrl(db, loginUser);
            fxmlLoader.setController(controller);
            AnchorPane pane = fxmlLoader.load();

            // Set the right-side content to the new pane
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openCourses() throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            if (loginUser.equals("Admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("course/ViewCoursesAdmin.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            } else if (loginUser.equals("Faculty")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("course/FacultyView.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("course/StudentView.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openEvents() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("events/EventManagement.fxml"));

            AnchorPane pane = fxmlLoader.load();

            // Replace the content of contentPane (the right side)
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openSubjects() throws IOException {
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

    @FXML
    void openProfile() throws SQLException, IOException {
        if(loginUser.equals("Faculty")){
            openFacultyProfile();
        }
        else if(loginUser.equals("Student")){   //If loginUser equals Student
            openStdProfile();
        }
        //Don't do else in case loged out
    }

    public void openFacultyProfile() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
            String[] IDColumn = {"Faculty ID"};
            List<String> id = db.getFilteredValues("Faculties", IDColumn, "Email", this.loginEmail);
//            System.out.println(id);
            FacultyProfileController facultycontroller = new FacultyProfileController(id.get(0), "faulty", db, contentPane);

            fxmlLoader.setController(facultycontroller);
            AnchorPane pane = fxmlLoader.load();

            // Set the right-side content to the new pane
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void openStdProfile() throws SQLException, IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student/StdViewProfile.fxml"));
            List<String> row = db.getRow("Students", "Email", loginEmail);
            String studentInfo = row.get(0) + ":" + row.get(1);
            StdProfileViewCtrl studentController = new StdProfileViewCtrl(db, "Student", studentInfo);

            fxmlLoader.setController(studentController);
            AnchorPane pane = fxmlLoader.load();

            // Set the right-side content to the new pane
            contentPane.getChildren().setAll(pane);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void openGridPane() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("events/EventManagement.fxml"));
            AnchorPane pane = fxmlLoader.load();
            eventPane.getChildren().setAll(pane);

            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-overview.fxml"));
            pane = fxmlLoader.load();
            facultyPane.getChildren().setAll(pane);

            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("course/AdminView.fxml"));
            pane = fxmlLoader.load();
            coursePane.getChildren().setAll(pane);

            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student/StdDashboard.fxml"));
            pane = fxmlLoader.load();
            studentPane.getChildren().setAll(pane);

            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("subject/admin-view.fxml"));
            pane = fxmlLoader.load();
            subjectPane.getChildren().setAll(pane);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void recentEvents() {
        try {
            // Load the AdminView.fxml into the right side of the screen
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("events/EventManagement.fxml"));
            AnchorPane pane = fxmlLoader.load();

            // Replace the content of contentPane (the right side)
            eventPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event){
        try {
            Parent loginScreen = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("login/login.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(loginScreen);
            stage.setScene(scene);
            stage.show();
            loginUser = "None";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}