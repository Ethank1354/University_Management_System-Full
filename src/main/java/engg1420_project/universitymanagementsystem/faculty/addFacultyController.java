package engg1420_project.universitymanagementsystem.faculty;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class addFacultyController {
    private Scene previousScene;
    private DatabaseManager db;
    private AnchorPane superAnchorPane;

    public addFacultyController(Scene prevtiousScene, DatabaseManager db, AnchorPane superAnchorPane) {
        this.previousScene = prevtiousScene;
        this.db = db;
        this.superAnchorPane = superAnchorPane;
    }

    @FXML
    private TextField facultyIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField degreeField;

    @FXML
    private TextField researchInterestField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField officeLocationField;

    @FXML
    private TextField coursesOfferedField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button uploadButton;

    String[] faculty = new String[9];

    @FXML
    private void save(ActionEvent event) {

        List<String> IDs = null;

        try {
            IDs = db.getColumnValues("Faculties", "Faculty ID");
            if(db.belongsToTable("Faculties", facultyIdField.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The faculty ID: " + facultyIdField.getText() +  " is already in use. Please choose an ID after " + IDs.get(IDs.size()-1));
                alert.showAndWait();
                
            }else if(facultyIdField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Faculty ID");
                alert.showAndWait();
                
            }else if(nameField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Faculty name");
                alert.showAndWait();

            }else if(degreeField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Degree");
                alert.showAndWait();

            }else if(researchInterestField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Research Interest");
                alert.showAndWait();

            }else if(emailField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Email");
                alert.showAndWait();

            }else if(officeLocationField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Office Location");
                alert.showAndWait();

            }else if(coursesOfferedField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Courses Offered");
                alert.showAndWait();

            }else if(passwordField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a Password");
                alert.showAndWait();

            }else{
                faculty[0] = facultyIdField.getText();
                faculty[1] = nameField.getText();
                faculty[2] = degreeField.getText();
                faculty[3] = researchInterestField.getText();
                faculty[4] = emailField.getText();
                faculty[5] = officeLocationField.getText();
                faculty[6] = coursesOfferedField.getText();
                faculty[7] = passwordField.getText();
                faculty[8] = facultyIdField.getText();



                try {
                    db.addRowToTable("Faculties", faculty);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-overview.fxml"));
                    fxmlLoader.setController(new facultyController(db, "admin", superAnchorPane));
                    AnchorPane pane = fxmlLoader.load();
                    superAnchorPane.getChildren().clear();
                    superAnchorPane.getChildren().add(pane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private void upload(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Photo");
            File file = fileChooser.showOpenDialog((Stage) uploadButton.getScene().getWindow());
            System.out.println(file.getName());

            File destination = null;

            URL resourceUrl = HelloApplication.class.getResource("images/");
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

            File newFile = new File(destination.getAbsolutePath() + "/" + file.getName() );

            faculty[8] = newFile.getName();

        } catch (IOException | URISyntaxException e) {
            faculty[8] = "images/BlankProfile.png";
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-overview.fxml"));
            fxmlLoader.setController(new facultyController(db, "admin", superAnchorPane));
            AnchorPane pane = fxmlLoader.load();
            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        /*if (previousScene != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
        }*/
    }
}