package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.Main;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Label labelName, labelStudentID1, labelAcadmeiclevel, labelTotal, labelBalance, labelPaid;

    @FXML TextField tfPaid, tfTotal;

    @FXML
    private Button btnEditTution;

    @FXML
    private ImageView imgViewProfile1;

    public void initialize() {

        //Main Page
        /*
        Image profile = null;
        try {
            profile = new Image(HelloApplication.class.getResourceAsStream("images/" + student.getPhotoLocation()));
        }catch (Exception e){
            imgViewProfile.setImage(new Image(HelloApplication.class.getResourceAsStream("images/BlankProfile.png")));
            imgViewProfile1.setImage(new Image(HelloApplication.class.getResourceAsStream("images/BlankProfile.png")));
        }



        imgViewProfile.setImage(profile);
        imgViewProfile1.setImage(profile);

         */

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

        //Tution Page
        labelName.setText(student.getName());
        labelStudentID1.setText(student.getStudentID());
        labelAcadmeiclevel.setText(student.getAcademicLvl());

        labelTotal.setText(Double.toString(student.getTution()));
        labelTotal.setText(Double.toString(student.getTutionPaid()));
        labelBalance.setText(Double.toString((student.getTution()) - student.getTutionPaid()));

        tfPaid.setDisable(true);
        tfTotal.setDisable(true);


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

    private int exceptionHandling() {
        //Name
        if (tfName.getText().length() <= 0 || doesContain(tfName.getText(), ' ') == 1) {
            return 1;
        }

        //Password
        if (tfPassword.getText().length() <= 0) {
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

        //Progress
        //Do it later

        //Semester
        if (tfSemester.getText().length() <= 0 || doesContain(tfSemester.getText(), ' ') == 1) {
            return 7;
        }

        return 0;
    }

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
            }

        }
    }

    @FXML
    void editTution(ActionEvent event) throws IOException {
        if (tfPaid.isDisabled() == true) {
            tfPaid.setDisable(false);
            tfTotal.setDisable(false);

        } else {
            tfPaid.setDisable(true);
            tfTotal.setDisable(true);
        }
    }

    @FXML
    void updateTution(ActionEvent event) throws IOException {
        student.setTutionPaid(student.getTutionPaid() + ((double) Double.parseDouble(tfPaid.getText())));
        student.setTution(student.getTution() + ((double) Double.parseDouble(tfPaid.getText())));

        labelTotal.setText(Double.toString(student.getTution()));
        labelTotal.setText(Double.toString(student.getTutionPaid()));
        labelBalance.setText(Double.toString(student.getTution() -  student.getTutionPaid()));
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




}

