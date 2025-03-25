//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StdCreateCtrl {

    private DatabaseManager db;
    private Scene previousScene;
    private String username;

    public StdCreateCtrl( DatabaseManager db, String username) {
        this.db = db;

        this.username = username;
    }

    @FXML
    private TextField tfName, tfAddress, tfPhone, tfEmail, tfPassword, tfProgress, tfThesis, tfSemester;

    @FXML
    private ChoiceBox cBoxAcmLvl;

    @FXML
    private Label label_ID;

    @FXML
    private Button btnExit;
    @FXML Button btnAddStd;

    @FXML
    private AnchorPane contentPane;

    public String generateID() throws SQLException {
        int largestID = 0;
        List<String> id = db.getColumnValues("Students", "Student ID");

        for (int i = 0; i < id.size(); i++) {
            if (Integer.parseInt(id.get(i).substring(1)) > largestID) {
                largestID = Integer.parseInt(id.get(i));
            }
        }

        return "S" + largestID++;
    }


    //Save Changes Button
    @FXML
    public void addStudent(ActionEvent event) throws IOException, SQLException {

        //Still need to figure out the subject/courses, student ID & photo
        String[] student = new String[12];

        student[0] = generateID();
        student[1] = tfName.getText();
        student[2] = tfAddress.getText();
        student[3] = tfPhone.getText();
        student[4] = tfEmail.getText();
        student[9] = tfThesis.getText();
        student[10] = tfProgress.getText();
        student[11] = tfPassword.getText();
        student[5] = (String) cBoxAcmLvl.getValue();
        student[6] = tfSemester.getText();
        //Subjects Registered
        //Photo

        try {
            db.addRowToTable("Students", student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            StdDashCtrl stdDashCtrl = new StdDashCtrl(db,username);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/StdDashboard.fxml"));
            fxmlLoader.setController(stdDashCtrl);
            AnchorPane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);
//            Parent root = fxmlLoader.load();
//
//            // Get current stage and store previous scene
//            Stage currentStage = (Stage) btnAddStd.getScene().getWindow();
//            Scene previousScene = currentStage.getScene(); // Save current scene
//
//
//
//            currentStage.setScene(new Scene(root, 600, 400));
//            currentStage.setTitle("Student Management System");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Exit Button
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
        cBoxAcmLvl.getItems().addAll("Undergraduate", "Graduate");

    }


}
