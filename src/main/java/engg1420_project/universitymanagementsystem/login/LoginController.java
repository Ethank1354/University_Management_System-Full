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
    public Button student_management, event_management, subject_management, faculty_management, course_management, main, dashboard, login, profile_management, Logout;
    @FXML
    public AnchorPane contentPane, eventPane, facultyPane, coursePane, subjectPane, studentPane;
    @FXML
    public Pane pane;
    @FXML
    public GridPane gridPane;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String loginUser;
    private static String loginEmail;
    private DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());


    //User Info
//    boolean didThisWork = importXLSXToDatabase("UMS_Data.xlsx");


//    private String[] studentID = {"S20250001", "S20250002", "S20250003", "S20250004", "S20250005", "S20250006", "S20250007", "S20250008", "S20250009", "S20250010"};


//    private String[] studentEmail = {"alice@example.edu", "bob.@example.edu", "carol@example.edu", "lucka@example.edu", "lee@example.edu", "brown@example.edu", "smith@example.edu", "jones@example.edu", "clarka@example.edu", "davis@example.edu"};
//
//    private String[] facultyID = {"F0001", "F0002", "F0003", "F0004", "F0005"};
//    private String[] facultyEmail = {"turing@university.edu", "bronte@university.edu", "hopper@university.edu", "copeland@university.edu", "gharabaghi@university.edu"};
//    private String[] facultyOffice = {"Room 201", "Room 202", "Lab 203", "Room 201", "Lab 202"};

    private String adminID = "admin123";
    private String adminEmail = "admin@uni";


    @FXML
    public void openDashboard(ActionEvent event) throws IOException, SQLException {
        if (checkLogin()) {
            try {
                if (loginUser.equals("Admin")) {
                    Parent dashboardFX = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("dashboard.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(dashboardFX);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Parent dashboardFX = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("menuUser.fxml")));
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

//    public boolean checkLogin() throws SQLException {
//
//        String[] col = {"Password"};
//
////        int facultySize = db.getTableHeaders("Faculties").size();  //Captures length of a faculty row in database
////        int studentSize = db.getTableHeaders("Students").size();  //Captures length of a student row in database
//        if (db.getFilteredValues("Faculties", col, "Email", email.getText().toString()).get(0).equals(id.getText().toString())){
//            loginUser = "Faculty";
//            loginID = id.getText();
//            return true;
//        }
//        else if (db.getFilteredValues("Students", col, "Email", email.getText().toString()).get(0).equals(id.getText().toString())){
//            loginUser = "Student";
//            loginID = id.getText();
//            return true;
//        }
//        else if (email.getText().toString().equals(adminEmail) && id.getText().toString().equals(adminID)) {
//            loginUser = "Admin";
//            return true;
//        }
//        else{
//            return false;
//        }
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
        System.out.println(email.getText() + " email without to string");
        System.out.println(email.getText().toString() + " email with to string");
        System.out.println(facultyRow.get(0));
        System.out.println(studentRow);
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

   /* for(int i=0; i<studentEmail.length; i++){
        if (email.getText().toString().equals(studentEmail[i]) && id.getText().toString().equals(studentID[i])){
            loginUser = "Student";
            return true;
        }
    }*/

    }


//        for (int i = 0; i < facultyEmail.length; i++) {
//            if (email.getText().toString().equals(facultyEmail[i]) && id.getText().toString().equals(facultyID[i])) {
//                loginUser = "Faculty";
//                loginID = id.getText();
//                return true;
//            }
//        }
//        for (int i = 0; i < studentEmail.length; i++) {
//            if (email.getText().toString().equals(studentEmail[i]) && id.getText().toString().equals(studentID[i])) {
//                loginUser = "Student";
//                return true;
//            }
//        }
//        if (email.getText().toString().equals(adminEmail) && id.getText().toString().equals(adminID)) {
//            loginUser = "Admin";
//            return true;
//        } else {
//            return false;
//        }
//    }

    @FXML
    void openDashboardMain(ActionEvent event) throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            if (loginUser.equals("Admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboardAdmin.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            } else if (loginUser.equals("Faculty")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashboardFaculty.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashboardStudent.fxml"));
                AnchorPane pane = fxmlLoader.load();
                // Replace the content of contentPane (the right side)
                contentPane.getChildren().setAll(pane);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@FXML
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
    }*/
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
            } else {
                if (loginUser.equals("Faculty")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-profile.fxml"));
                    facultyController facultycontroller = new facultyController(db, "admin", contentPane);
                    fxmlLoader.setController(facultycontroller);
                    AnchorPane pane = fxmlLoader.load();
                } else if (loginUser.equals("Student")) {

                } else {

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
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
    void openEvents(ActionEvent event) throws IOException {
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

    @FXML
    public void openProfile() throws SQLException, IOException {
        if(loginUser.equals("Faculty")){
            openFacultyProfile();
        }
        else{   //If loginUser equals Student
            openStdProfile();
        }
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
    void openGridPane(ActionEvent event) throws IOException {
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

/*
    // This method will be automatically called when the page loads
    @FXML
    public void initialize() {
        loadFXMLToGridPane("student/StdDashboard.fxml", 0, 0);  // Top-left
        loadFXMLToGridPane("subject/admin-view.fxml", 0, 1);  // Top-right
        loadFXMLToGridPane("faculty/faculty-overview.fxml", 1, 0);  // Bottom-left
        loadFXMLToGridPane("events/EventManagement.fxml", 1, 1);   // Bottom-right
        loadFXMLToGridPane("course/AdminView.fxml", 2, 0);         // Lower-left
    }

    // Helper method to load an FXML file into the specified cell of the GridPane
    private void loadFXMLToGridPane(String fxmlFile, int row, int col) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();

            // Add the loaded FXML to the specified cell in the GridPa
            gridPane.add(root, col, row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/


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
}
