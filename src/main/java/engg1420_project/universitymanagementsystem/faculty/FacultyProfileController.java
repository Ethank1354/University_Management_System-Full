package engg1420_project.universitymanagementsystem.faculty;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.student.StdProfileViewCtrl;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

public class FacultyProfileController {
    private boolean editable;
    private Faculty faculty;
    private DatabaseManager db;
    private Scene previousScene;
    private boolean isEditing = false;
    private boolean previous;
    private AnchorPane superAnchorPane;
    private String access;
    private String studentID;

    public FacultyProfileController(String facultyInfo, String access, DatabaseManager db, AnchorPane superAnchorPane, String studentID) throws SQLException {
        this.db = db;
        this.superAnchorPane = superAnchorPane;
        String[] parts = facultyInfo.split(":");
        this.faculty = new Faculty(parts[0], this.db);
        this.editable = !access.equalsIgnoreCase("student");
        this.access = access;
        this.studentID = studentID;
        if(access.equalsIgnoreCase("admin") || access.equalsIgnoreCase("student")) {
            previous = true;
        }else{
            previous = false;
        }
    }

    @FXML
    private Tab coursesTab;

    @FXML
    private Tab profileTab;

    @FXML
    private Button editButton;

    @FXML
    private ListView<String> coursesListView;

    @FXML
    private Label nameLabel, roomTextLabel, emailTextLabel;

    @FXML
    private Label researchTextLabel;

    @FXML
    private Label roomLabel, emailLabel, researchLabel;

    @FXML
    private TextField roomField, emailField, researchField;

    @FXML
    private Button backButton;

    @FXML
    private ImageView profileImage;

    @FXML
    private  TextField passwordText;

    @FXML
    private Button chooseImageButton;

    @FXML
    private Label degreeLabel;

    @FXML
    private TextField degreeField;

    @FXML
    public void initialize() {
        passwordText.setText(faculty.getPassword());
        passwordText.setVisible(false);
        chooseImageButton.setVisible(false);
        backButton.setVisible(previous);
        profileTab.setClosable(false);

        coursesTab.setClosable(false);
        coursesTab.setDisable(!editable);

        degreeLabel.setText(faculty.getDegree());

        ImageView backImage = new ImageView(new Image(HelloApplication.class.getResourceAsStream("images/backButton.png")));
        backImage.setFitHeight(30);
        backImage.setFitWidth(30);
        backButton.setGraphic(backImage);

        profileImage.setImage(new Image(HelloApplication.class.getResourceAsStream("images/BlankProfile.png")));

        Image profile = null;
        try {
            profile = new Image(HelloApplication.class.getResourceAsStream("images/" + faculty.getProfilePhotoLocation()));
        }catch (Exception e){

        }



        profileImage.setImage(profile);

        nameLabel.setText(faculty.getName());
        researchLabel.setText(faculty.getResearchInterest());
        roomLabel.setText(faculty.getOfficeLocation());
        emailLabel.setText(faculty.getEmail());
        degreeField.setText(faculty.getDegree());

        roomField.setText(faculty.getOfficeLocation());
        emailField.setText(faculty.getEmail());
        researchField.setText(faculty.getResearchInterest());

        roomField.setVisible(false);
        emailField.setVisible(false);
        researchField.setVisible(false);

        editButton.setVisible(editable);

        coursesListView.getItems().addAll(faculty.getCourses().split(","));

        coursesListView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu coursesMenu = new ContextMenu();

            MenuItem viewStudents = new MenuItem();
            viewStudents.textProperty().bind(Bindings.format("View students in \"%s\"", cell.itemProperty()));
            viewStudents.setOnAction(event -> openStudentsList(cell.getItem()));
            viewStudents.setDisable(!editable);

            coursesMenu.getItems().add(viewStudents);
            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(coursesMenu);
                }
            });

            return cell;
        });
    }

    @FXML
    private void toggleEdit(ActionEvent event) {
        if (!isEditing) {
            editButton.setText("Save");

            roomLabel.setVisible(false);
            emailLabel.setVisible(false);
            researchLabel.setVisible(false);

            if(this.access.equals("admin")) {
                degreeLabel.setVisible(false);
                degreeField.setVisible(true);
            }

            roomField.setVisible(true);
            emailField.setVisible(true);
            researchField.setVisible(true);
            passwordText.setVisible(true);
            chooseImageButton.setVisible(true);
        } else {
            editButton.setText("Edit");

            roomLabel.setText(roomField.getText());
            emailLabel.setText(emailField.getText());
            researchLabel.setText(researchField.getText());
            degreeLabel.setText(degreeField.getText());

            faculty.setOfficeLocation(roomField.getText());
            faculty.setEmail(emailField.getText());
            faculty.setResearchInterest(researchField.getText());
            faculty.setPassword(passwordText.getText());
            faculty.setName(degreeField.getText());

            faculty.updateInfo();

            roomLabel.setVisible(true);
            emailLabel.setVisible(true);
            researchLabel.setVisible(true);
            degreeLabel.setVisible(true);

            roomField.setVisible(false);
            emailField.setVisible(false);
            researchField.setVisible(false);
            passwordText.setVisible(false);
            degreeField.setVisible(false);
            chooseImageButton.setVisible(false);
        }

        isEditing = !isEditing;
    }

    @FXML
    private void goBack(ActionEvent event) {
        if(this.studentID == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/faculty-overview.fxml"));
                fxmlLoader.setController(new facultyController(db, "admin", superAnchorPane));
                AnchorPane pane = fxmlLoader.load();
                superAnchorPane.getChildren().clear();
                superAnchorPane.getChildren().add(pane);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                // Load the FXML and set the controller
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student/StdViewProfile.fxml"));
                StdProfileViewCtrl profileController = new StdProfileViewCtrl(db, access.substring(0, 1).toUpperCase() + access.substring(1), studentID);
                fxmlLoader.setController(profileController);
                AnchorPane pane = fxmlLoader.load();

                // Set the right-side content to the new pane
                superAnchorPane.getChildren().setAll(pane);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void openStudentsList(String course) {

        try {
            Stage currentStage = (Stage) coursesListView.getScene().getWindow();
            Scene currentScene = currentStage.getScene();

            studentListController studentListController = new studentListController(db, currentScene, course, superAnchorPane, faculty.getFacultyId(), this.access);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("faculty/students-list.fxml"));
            fxmlLoader.setController(studentListController);
            AnchorPane pane = fxmlLoader.load();
            superAnchorPane.getChildren().clear();
            superAnchorPane.getChildren().add(pane);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void chooseImage(ActionEvent event) {
        try {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        File file = fileChooser.showOpenDialog((Stage) profileImage.getScene().getWindow());
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

        //System.out.println(destination.getAbsolutePath());

        File newFile = new File(destination.getAbsolutePath() + "/" + file.getName() );

        //System.out.println(newFile.getAbsolutePath());
        faculty.setProfilePhotoLocation(newFile.getName());
        //faculty.updateProfilePhoto();

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
}