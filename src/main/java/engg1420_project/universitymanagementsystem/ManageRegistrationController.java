package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ManageRegistrationController {
    @FXML
    private ListView<String> registeredStudentsListView;

    @FXML
    public void initialize() {
        // Load registered students (dummy data for now)
        registeredStudentsListView.getItems().addAll("Student A", "Student B");
    }
}