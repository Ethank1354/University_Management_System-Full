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
    private String cellItem;
    private Scene previousScene;
    private String username;
    private Student student;


    public StdProfileEditCtrl(DatabaseManager db, String cellItem, String username) throws SQLException {
        this.db = db;
        this.cellItem = cellItem;
        String[] id = cellItem.split(":"); //The first part of this string is the id of the student, the second part is the name
        this.student = new Student(id[0], db);
        this.username = username;

    }

    @FXML
    private TextField tfName, tfAddress, tfPhone, tfEmail, tfPassword, tfThesis, tfProgress,tfSemester;

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
        student.setName(tfName.getText());
        student.setAddress(tfAddress.getText());
        student.setPhoneNumber(tfPhone.getText());
        student.setEmail(tfEmail.getText());
        student.setPassword(tfPassword.getText());
        student.setThesis(tfThesis.getText());
        student.setAcademicProgress((double) Double.parseDouble(tfProgress.getText()));
        student.setAcademicLvl((String) cBoxAcmLvl.getValue());
        student.setSemster(tfSemester.getText());

        student.updateStudent();
      // db.updateRowInTable("Students", "Student ID" , student.getStudentID(), student.studentToList());

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
        tfSemester.setText(student.getSemster());
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




