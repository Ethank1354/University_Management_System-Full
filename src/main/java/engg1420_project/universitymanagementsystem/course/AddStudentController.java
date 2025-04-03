package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class AddStudentController {

    @FXML private Button closeButton;

    private ManageEnrollmentsController manageEnrollmentsController;
    private DatabaseManager db;

    // Constructor to initialize DatabaseManager
    public AddStudentController() {
        this.db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());
    }

    public void setManageEnrollmentsController(ManageEnrollmentsController controller) {
        this.manageEnrollmentsController = controller;
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

