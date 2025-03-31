//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StdProfileViewCtrl  {

    private DatabaseManager db;
    private String studentInfo;
    private String access;
    private boolean canEdit;
    private Student student;
    private Scene previousScene;
    private boolean previous;
    private String username;
    private String ID;

    public StdProfileViewCtrl(String studentInfo, String access, DatabaseManager db, String username) throws SQLException {
        this.db = db;
        this.studentInfo = studentInfo;
        String[] parts = studentInfo.split(":");
        this.student = new Student(parts[0], db);
        this.username = username;
    }

    public StdProfileViewCtrl(DatabaseManager db, String access, String studentInfo) throws SQLException {
        this.db = db;
        this.studentInfo = studentInfo;
        String[] parts = studentInfo.split(":");
        this.student = new Student(parts[0], db);
        this.access = access;
    }

    /*
    public StdProfileViewCtrl(DatabaseManager db, String access, String ID) {
        this.db = db;
        this.access = access;
        this.ID = ID;
    }
     */

    @FXML
    private ListView<String> courseListView;

    @FXML
    private ListView<String> subjectListView;

    @FXML
    private Label labelStdName, labelStdID, labelStdEmail, labelStdPhone, labelStdAddress, labelSemester, labelAcmLvl, labelThesis;
    @FXML
    private Label labelTotalAmt, labelAmtPaid, labelAmtLeft;

    @FXML
    private ProgressBar barProgramProgress;

    @FXML
    public ImageView profilePhoto;

    @FXML
    private Button btnExit, btnEdit;

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void edit (ActionEvent event) throws IOException {
        try{
            Stage currentStage = (Stage) btnEdit.getScene().getWindow();
            Scene previousScene = currentStage.getScene(); // Save current scene

            //StdProfileEditCtrl stdProfileEditCtrl = new StdProfileEditCtrl(db, studentInfo, username);
            editStudentProfile editStudentProfile = new editStudentProfile(db, studentInfo, access, username);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/studentProfileEdit.fxml"));
            fxmlLoader.setController(editStudentProfile);

            AnchorPane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void exit (ActionEvent event) throws IOException {
        try {
            StdDashCtrl stdDashCtrl = new StdDashCtrl(db,username);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/StdDashboard.fxml"));
            fxmlLoader.setController(stdDashCtrl);

            AnchorPane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Photo File Stuff
    //Just what ethan did (will probably just be the same way he did it)
    /*
    @FXML
    private void chooseImage(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Photo");
            File file = fileChooser.showOpenDialog((Stage) profilePhoto.getScene().getWindow());
            System.out.println(file.getName());


            File destination = null;

            URL resourceUrl = StdDashApp.class.getResource("images/");
            if (resourceUrl != null) {
                destination = new File(resourceUrl.toURI());
                if (destination.isDirectory()) {
                    FileUtils.copyFileToDirectory(file, destination);
                } else {
                    System.out.println("Destination is not a directory.");
                }
            } else {
                System.out.println("Resource path is null.");
            }

            //System.out.println(destination.getAbsolutePath());

            File newFile = new File(destination.getAbsolutePath() + "/" + file.getName() );

            //System.out.println(newFile.getAbsolutePath());
            faculty.setProfilePhotoLocation(newFile.getName());
            faculty.updateProfilePhoto();

            FileInputStream imageFile = new FileInputStream(newFile);

            Image image = new Image(imageFile);
            profileImage.setImage(image);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

     */

    @FXML
    public void initialize() {

        Image profile = null;
        try {
            profile = new Image(HelloApplication.class.getResourceAsStream("images/" + student.getPhotoLocation()));
        }catch (Exception e){
            profilePhoto.setImage(new Image(HelloApplication.class.getResourceAsStream("images/default.png")));

        }

        profilePhoto.setImage(profile);
        /*
        if (access == "Faculty") {
            btnEdit.setDisable(true);
        } else {
            btnEdit.setDisable(false);
        }
//Also include tution information
         */

        labelTotalAmt.setText("$"+ student.getTution().toString());
        labelAmtPaid.setText("$"+ student.getTutionPaid().toString());
        labelAmtLeft.setText("$"+ (student.getTution() - student.getTutionPaid()));

        labelStdName.setText(student.getName());
        labelStdID.setText(student.getStudentID());
        labelStdEmail.setText(student.getEmail());
        labelStdPhone.setText(student.getPhoneNumber());
        labelStdAddress.setText(student.getAddress());
        labelSemester.setText(student.getSemster());
        labelAcmLvl.setText(student.getAcademicLvl());
        labelThesis.setText(student.getThesis());

        barProgramProgress.setProgress(student.getAcademicProgress());

/*
        if (student.getSubjects() != null) {
            String[] subjects = student.getSubjects();
            String[] grades = student.getGrades();

            List<String> subjectList = new ArrayList<>();

            for (int i = 0; i < subjects.length; i++) {
                subjectList.add(subjects[i] + " : " + grades[i]);
            }

            for (int i = 0; i < subjects.length; i++) {
                subjectListView.getItems().addAll(subjectList);
            }
        } else {
            subjectListView.getItems().add("No Registered Subjects");
        }

        Image profile = null;
        try {
            profile = new Image(HelloApplication.class.getResourceAsStream("images/" + student.getPhotoLocation()));
        }catch (Exception e){
            profilePhoto.setImage(new Image(HelloApplication.class.getResourceAsStream("images/BlankProfile.png")));
        }

        profilePhoto.setImage(profile);

 */

    }
}
