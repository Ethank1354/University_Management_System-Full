package engg1420_project.universitymanagementsystem.empty;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DashboardAdmin {

    @FXML
    public AnchorPane contentPane;
/*
    public void openDashboardMain(ActionEvent event) throws IOException {
        try {
            // Load the AdminView.fxml into the right side of the screen
            FXMLLoader fxmlLoader;
            if(LoginController.loginUser.equals("Admin")){
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashBoardAdmin.fxml"));
            }
            else if(LoginController.loginUser.equals("Faculty")) {
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashBoardFaculty.fxml"));
            }
            else {
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboardStudentMain.fxml"));
            }
            AnchorPane pane = fxmlLoader.load();


            // Replace the content of dashboard_main (the right side)
            contentPane.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   */
}
