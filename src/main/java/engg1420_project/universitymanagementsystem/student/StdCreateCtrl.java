//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
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
import java.util.List;

public class StdCreateCtrl {

    private DatabaseManager db;
    private Scene previousScene;
    private String username;


    private String photoName;

    public StdCreateCtrl(DatabaseManager db, String username) {
        this.db = db;

        this.username = username;
    }

    @FXML
    private TextField tfName, tfAddress, tfPhone, tfEmail, tfPassword, tfProgress, tfThesis, tfSemester;

    @FXML
    private ChoiceBox cBoxAcmLvl;

    @FXML
    private Label label_ID, labelError;

    @FXML
    private Button btnExit;
    @FXML Button btnAddStd;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private ImageView imgViewProfile;

    public String generateID() throws SQLException {
        int largestID = 0;
        List<String> id = db.getColumnValues("Students", "Student ID");

        for (int i = 0; i < id.size(); i++) {
            if (Integer.parseInt(id.get(i).substring(1)) > largestID) {
                largestID = Integer.parseInt(id.get(i).substring(1));
            }
        }

        return "S" + (largestID +1);
    }


    //Save Changes Button
    @FXML
    public void addStudent(ActionEvent event) throws IOException, SQLException {
        if (exceptionHandling() == 0) {

            //Still need to figure out the subject/courses, student ID & photo
            String[] student = new String[15];

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
            String[] photoInfo = new String[2];
            photoInfo[0] = student[0];
            if (photoName == null) {
                photoInfo[1] = "default.png";
            } else {
                photoInfo[1] = photoName;
            }
            student[7] = photoInfo[1];



            try {
                db.addRowToTable("Students", student);
               // db.addRowToTable("Photos", photoInfo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                StdDashCtrl stdDashCtrl = new StdDashCtrl(db, username);
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
            /*
            student.setProfilePhotoLocation(newFile.getName());
            student.updateProfilePhoto();

             */

            FileInputStream imageFile = new FileInputStream(newFile);

            Image image = new Image(imageFile);
            imgViewProfile.setImage(image);
            photoName = file.getName();



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }



    @FXML
    public void initialize() {
        cBoxAcmLvl.getItems().addAll("Undergraduate", "Graduate");
        labelError.setText("");

    }


}
