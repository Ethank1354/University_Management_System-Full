package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.Main;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class editStudentProfile {
    private DatabaseManager db;
    private Student student;
    private String access;
    private String cellItem;
    private String username;

    public editStudentProfile(DatabaseManager db, String cellItem, String access, String username) throws SQLException {
        this.db = db;
        this.cellItem = cellItem;
        String[] id = cellItem.split(":"); //The first part of this string is the id of the student, the second part is the name
        this.student = new Student(id[0], db);
        this.access = access;
        this.username = username;
    }

    @FXML
    private AnchorPane contentPane;
    private Tab tabProfile, tabCourse, tabTution;

    //Main Page
    @FXML
    private TextField tfName, tfPassword, tfEmail, tfPhone, tfAddress, tfThesis, tfSemester, tfProgress;

    @FXML
    private ChoiceBox<String> chBoxLevel;

    @FXML
    private Button btnSave, btnUpload;

    @FXML
    private ImageView imgViewProfile;

    @FXML
    private Label labelStudentID, labelError;

    //Tution Page
    @FXML
    private Label  labelTotal, labelBalance, labelPaid, labelEditPaid, labelEditOwed;

    @FXML TextField tfPaid, tfTotal;

    @FXML
    private Button btnEditTution, btnUpdateTution;


    //Course Page
    @FXML
    private ListView subjectListView;

    public void initialize() {
        if (access.equals("Student")) {
            btnSave.setVisible(false);
            tfProgress.setVisible(false);
            tfSemester.setVisible(false);
            tfThesis.setVisible(false);
            chBoxLevel.setVisible(false);
        } else if (access.equals("Admin")) {
            btnSave.setVisible(true);
            tfProgress.setVisible(true);
            tfSemester.setVisible(true);
            tfThesis.setVisible(true);
            chBoxLevel.setVisible(true);

        }

        //Main Page

        Image profile = null;
        try {
            profile = new Image(HelloApplication.class.getResourceAsStream("images/" + student.getPhotoLocation() + ".png"));
        }catch (Exception e){
            imgViewProfile.setImage(new Image(HelloApplication.class.getResourceAsStream("images/default.png")));

        }

        imgViewProfile.setImage(profile);




        labelStudentID.setText(student.getStudentID());
        labelError.setText("");

        chBoxLevel.getItems().addAll("Undergraduate", "Graduate");
        tfName.setText(student.getName());
        tfPassword.setText(student.getPassword());
        tfEmail.setText(student.getEmail());
        tfPhone.setText(student.getPhoneNumber());
        tfAddress.setText(student.getAddress());
        tfThesis.setText(student.getThesis());
        tfProgress.setText(Double.toString(student.getAcademicProgress()));
        tfSemester.setText(student.getSemster());

        if (student.getAcademicLvl().equals("Undergraduate")) {
            chBoxLevel.setValue("Undergraduate");
        } else if (student.getAcademicLvl().equals("Graduate")) {
            chBoxLevel.setValue("Graduate");
        } else {
            chBoxLevel.setValue("Undergraduate");
        }

        //Course Page
        if (student.getSubjects() != null) {
            ArrayList<String> subjects = student.getSubjects();

            ArrayList<String> grades = student.getGrades();

            List<String> subjectList = new ArrayList<>();

            for (int i = 0; i < subjects.size(); i++) {
                subjectList.add(subjects.get(i) + ": " + grades.get(i));
            }


                subjectListView.getItems().addAll(subjectList);

        } else {
            subjectListView.getItems().add("No Registered Subjects");
        }

        subjectListView.setCellFactory(lv -> {

            ListCell<String> cell = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();

           /*
           change to not delete the student
            */
            MenuItem dropItem = new MenuItem();
            dropItem.textProperty().bind(Bindings.format("Drop \"%s\"", cell.itemProperty()));
            dropItem.setOnAction(event -> {
                String item = cell.getItem();
                subjectListView.getItems().remove(item);
                String[] parts = item.split(":");
                System.out.println(parts[0]);
                student.deleteSubjects(parts[0]);
                try {
                    student.updateStudent();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });

            //Adding all the options to the click down menu
            contextMenu.getItems().addAll(dropItem);

            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            return cell;
        });

        //Tution Page


        labelTotal.setText(Double.toString(student.getTution()));
        labelPaid.setText(Double.toString(student.getTutionPaid()));
        labelBalance.setText(Double.toString((student.getTution()) - student.getTutionPaid()));

        //Visible Stuff
        tfPaid.setVisible(false);
        tfTotal.setVisible(false);
        labelEditPaid.setVisible(false);
        labelEditOwed.setVisible(false);
        btnUpdateTution.setVisible(false);


    }

    private int doesContain(String string, char c) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == c) {
                count++;
            }
        }

        if (count <= 0) {
            return 1;
        }
        return 0;
    }

    /*
    Exception Handling
        Checking all the text fields to make sure the inputs are correct
        mainly checks to see that the fields actually filled or that its only numbers or certain characters
     */
    private int exceptionHandling() {
        //Name
        if (tfName.getText().length() <= 0 || doesContain(tfName.getText(), ' ') == 1) {
            return 1;
        }

        //Password
        if (tfPassword.getText().length() <= 1) {
            tfPassword.clear();
            return 2;
        }

        //Email
        if (tfEmail.getText().length() <= 0 || doesContain(tfEmail.getText(), '@') == 1) {
            return 3;
        }

        //Address
        if (tfAddress.getText().length() <= 0 || doesContain(tfAddress.getText(), '.') == 1 || doesContain(tfAddress.getText(), ' ') == 1) {
            return 4;
        }

        //Phone
        //Do it later
        if (tfPhone.getText().length() <= 0 || doesContain(tfPhone.getText(), '-') == 1 || tfPhone.getText().matches("\\d+")) {
            return 5;
        }

        //Progress
        if (tfProgress.getText().length() <= 0 || tfPhone.getText().matches("\\d+") || doesContain(tfProgress.getText(), '.') == 1) {
            return 6;
        }

        //Semester
        if (tfSemester.getText().length() <= 0 || doesContain(tfSemester.getText(), ' ') == 1) {
            return 7;
        }

        return 0;
    }


    /*
    Saves the Changes
        first, it uses the exception handling method to make sure every input is up to par
        next it updates all the changes to the dummy student and tries to update the student in the database

        if successful it will bring you back to the dashboard
        if it isn't it shows the error found with the exception handling

        NEED TO UPDATE THE DESTNATIONS DEPENDING ON THE ACCESS LEVEL
     */
    @FXML
    void saveChanges(ActionEvent event) throws IOException, SQLException {
        if (exceptionHandling() == 0) {
            student.setName(tfName.getText());
            student.setAddress(tfAddress.getText());
            student.setPhoneNumber(tfPhone.getText());
            student.setEmail(tfEmail.getText());
            student.setPassword(tfPassword.getText());
            student.setThesis(tfThesis.getText());
            student.setAcademicProgress((double) Double.parseDouble(tfProgress.getText()));
            student.setAcademicLvl((String) chBoxLevel.getValue());
            student.setSemster(tfSemester.getText());
            student.updateStudent();


            try {
                StdDashCtrl stdDashCtrl = new StdDashCtrl(db, access);
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/StdDashboard.fxml"));
                fxmlLoader.setController(stdDashCtrl);

                AnchorPane pane = fxmlLoader.load();
                contentPane.getChildren().setAll(pane);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (exceptionHandling() == 1) {
                labelError.setText("Bro WTF fix yo names stinky face");
            } else if (exceptionHandling() == 2) {
                labelError.setText("Bro WTF fix yo passwords stinky face");
            } else if (exceptionHandling() == 3) {
                labelError.setText("Please Re-enter your email address");
            } else if (exceptionHandling() == 4) {
                labelError.setText("Please Re-enter your address");
            } else if (exceptionHandling() == 7) {
                labelError.setText("Please Re-enter your smesmer?");
            } else if (exceptionHandling() == 5) {
                labelError.setText("Please Re-enter your phone number");
            } else if (exceptionHandling() == 6) {
                labelError.setText("Please Re-enter your academic progress");
            }

        }
    }

    /*
    Makes the tution text fields visible or invisble when the buttons are pressed
     */
    @FXML
    void editTution(ActionEvent event) throws IOException {
        if (!tfPaid.isVisible()) {
            tfPaid.setVisible(true);
            tfTotal.setVisible(true);
            labelEditPaid.setVisible(true);
            labelEditOwed.setVisible(true);
            btnUpdateTution.setVisible(true);



        } else {
            tfPaid.setVisible(false);
            tfTotal.setVisible(false);
            labelEditPaid.setVisible(false);
            labelEditOwed.setVisible(false);
            btnUpdateTution.setVisible(false);
        }
    }

    /*
    What do you think
    just read it
     */
    @FXML
    void updateTution(ActionEvent event) throws IOException {
        student.setTutionPaid(student.getTutionPaid() + ((double) Double.parseDouble(tfPaid.getText())));
        student.setTution(student.getTution() + ((double) Double.parseDouble(tfPaid.getText())));

        labelTotal.setText(Double.toString(student.getTution()));
        labelPaid.setText(Double.toString(student.getTutionPaid()));
        labelBalance.setText(Double.toString(student.getTution() -  student.getTutionPaid()));

        tfPaid.setText("");
        tfTotal.setText("");
    }

    @FXML
    private void chooseImage(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Photo");
            File file = fileChooser.showOpenDialog((Stage) imgViewProfile.getScene().getWindow());
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
            student.setProfilePhotoLocation(newFile.getName());
            student.updateProfilePhoto();

            FileInputStream imageFile = new FileInputStream(newFile);

            Image image = new Image(imageFile);
            imgViewProfile.setImage(image);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
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
    public void addCourse (ActionEvent event) throws IOException {
        try {
            addStudentCourse addStudentCourse = new addStudentCourse(db, student, username, access);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/studentCourseAdd.fxml"));
            fxmlLoader.setController(addStudentCourse);

            AnchorPane pane = fxmlLoader.load();
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

