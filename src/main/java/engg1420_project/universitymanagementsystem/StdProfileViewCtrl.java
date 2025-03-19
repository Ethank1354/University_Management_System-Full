//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import org.apache.commons.io.FileUtils;


import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;


public class StdProfileViewCtrl  {

    private DatabaseManager db;
    private String studentInfo;
    private String access;
    private boolean canEdit;
    private Student student;
    private Scene previousScene;
    private boolean previous;
    private String username;

    public StdProfileViewCtrl(String studentInfo, String access, DatabaseManager db, String username) throws SQLException {
        this.db = db;
        String[] parts = studentInfo.split(":");
        this.student = new Student(parts[0], db);
        /*
        this.canEdit = !access.equals("student");
        if(access.equals("admin")) {
            previous = true;
        }else{
            previous = false;
        }

         */
        this.username = username;
    }

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
    private Button btnExit;

    @FXML
    private AnchorPane contentPane;



    @FXML
    public void exit (ActionEvent event) throws IOException {
        try {
            StdDashCtrl stdDashCtrl = new StdDashCtrl(db,username);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StdDashboard.fxml"));
            fxmlLoader.setController(stdDashCtrl);

            AnchorPane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);
//            Parent root = fxmlLoader.load();

            // Get current stage and store previous scene
//            Stage currentStage = (Stage) btnExit.getScene().getWindow();
//            Scene previousScene = currentStage.getScene(); // Save current scene
//
//
//
//            currentStage.setScene(new Scene(root, 600, 400));
//            currentStage.setTitle("Studet Management System");

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

        labelStdName.setText(student.getName());
        labelStdID.setText(student.getStudentID());
        labelStdEmail.setText(student.getEmail());
        labelStdPhone.setText(student.getPhoneNumber());
        labelStdAddress.setText(student.getAddress());
        labelSemester.setText(student.getSemster());
        labelAcmLvl.setText(student.getAcademicLvl());
        labelThesis.setText(student.getThesis());

        String[] subjects = student.getSubjects();

        for (int i = 0; i < subjects.length; i++) {
            subjectListView.getItems().add(subjects[i]);
        }

        /* Figure out Image things
            Get progress bar working
            tutition calculations
            course and subject lists
            making certain things not visible
         */




    }
}
