package engg1420_project.universitymanagementsystem.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class dashboardAdmin {

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        loadFXMLIntoGridPane("student/StdDashboard.fxml", 0, 1);
        loadFXMLIntoGridPane("subject/admin-view.fxml", 1, 1);
        loadFXMLIntoGridPane("faculty/faculty-overview.fxml", 0, 2);
        loadFXMLIntoGridPane("events/EventManagement.fxml", 1, 2);
        loadFXMLIntoGridPane("course/AdminView.fxml", 1, 3);
    }

    private void loadFXMLIntoGridPane(String fxmlPath, int column, int row) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();
            gridPane.add(pane, column, row);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + fxmlPath);
        }
    }
}
