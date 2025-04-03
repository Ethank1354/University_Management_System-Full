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
    private ListView<String> subjectListView;

    @FXML
    private Label labelStdName, labelStdID, labelStdEmail, labelStdPhone, labelStdAddress, labelSemester, labelAcmLvl, labelThesis;
    @FXML
    private Label labelTotalAmt, labelAmtPaid, labelAmtLeft, ProgramProgress, TutionInfo, TotalAmount, AmountPaid, AmountLeft;

    @FXML
    private Separator horzLine, vertLine;

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


    @FXML
    public void initialize() {

        if (access.equals("Faculty")) {
            btnEdit.setVisible(false);
            barProgramProgress.setVisible(false);
            subjectListView.setVisible(false);
            horzLine.setVisible(false);
            vertLine.setVisible(false);
            TutionInfo.setVisible(false);
            TotalAmount.setVisible(false);
            AmountPaid.setVisible(false);
            AmountLeft.setVisible(false);
        } else {
            btnEdit.setVisible(true);
            barProgramProgress.setVisible(true);
            subjectListView.setVisible(true);
            horzLine.setVisible(true);
            vertLine.setVisible(true);
            TutionInfo.setVisible(true);
            TotalAmount.setVisible(true);
            AmountPaid.setVisible(true);
            AmountLeft.setVisible(true);
        }

        Image profile = null;
        try {
            profile = new Image(HelloApplication.class.getResourceAsStream("images/" + student.getPhotoLocation()));
        }catch (Exception e){
            profilePhoto.setImage(new Image(HelloApplication.class.getResourceAsStream("images/default.png")));

        }

        profilePhoto.setImage(profile);


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

        if (student.getSubjects() == null  || student.getSubjects().contains("")) {
            subjectListView.getItems().add("No Registered Subjects");
        } else {

            ArrayList<String> subjects = student.getSubjects();
            ArrayList<String> grades = student.getGrades();

            List<String> subjectList = new ArrayList<>();

            for (int i = 0; i < subjects.size(); i++) {
                subjectList.add(subjects.get(i) + ": " + grades.get(i));
            }


                subjectListView.getItems().addAll(subjectList);

        }



    }
}
