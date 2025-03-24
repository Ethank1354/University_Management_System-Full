//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StdProfileEditCtrl  {

    private DatabaseManager db;
    private String studentInfo;
    private Scene previousScene;
    private String username;
    private Student student;


    public StdProfileEditCtrl(DatabaseManager db, String studentInfo, String username) throws SQLException {
        this.db = db;
        this.studentInfo = studentInfo;
        String[] parts = studentInfo.split(":");
        this.student = new Student(parts[0], db);
        this.username = username;

    }

    @FXML
    private TextField tfName, tfAddress, tfPhone, tfEmail, tfPassword, tfThesis, tfProgress;

    @FXML
    private ChoiceBox cBoxAcmLvl;

    @FXML
    private Label labelStdID;

    @FXML
    private Button btnSave, btnExit, btnUpload;

    @FXML
    private AnchorPane contentPane;

    //Save changes button
    @FXML
    void saveChanges(ActionEvent event) throws IOException, SQLException {

       db.updateRowInTable("Students", "Student ID" , student.getStudentID(), student.studentToList());

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
    void exit(ActionEvent event) throws IOException {
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
        cBoxAcmLvl.getItems().addAll("Undergraduate", "Graduate");

        tfName.setText(student.getName());
        //labelStdID.setText(student.getStudentID());
        tfEmail.setText(student.getEmail());
        tfPhone.setText(student.getPhoneNumber());
        tfAddress.setText(student.getAddress());
        tfThesis.setText(student.getThesis());
        tfPassword.setText(student.getPassword());
        tfProgress.setText(Double.toString(student.getAcademicProgress()));
        //labelSemester.setText(student.getSemster());
        if (student.getAcademicLvl().equals("Undergraduate")) {
            cBoxAcmLvl.setValue("Undergraduate");
        } else if (student.getAcademicLvl().equals("Graduate")) {
            cBoxAcmLvl.setValue("Graduate");
        } else {
            cBoxAcmLvl.setValue("Undergraduate");
        }
    }

}




