//Mateo
package engg1420_project.universitymanagementsystem.course;


import engg1420_project.universitymanagementsystem.subject.Subjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;


public class AdminViewController {

    @FXML
    private Stage stage;
    private Scene scene;

    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TableView<Subjects> subjectsTable;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;

    private ObservableList<Subjects> subjectsData = FXCollections.observableArrayList();

    @FXML
    public void viewSubjects(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-view-subjects.fxml")));

        // Create a new stage (window)
        Stage newStage = new Stage();
        newStage.setTitle("View Subjects");
        newStage.setScene(new Scene(root));
        newStage.show(); // Show alongside the admin screen
    }
}
/*
    @FXML
    void determineRole() {

    Scanner scanner = new Scanner(System.in);
    System.out.print("What role are you (Student, admin, or faculty): ");
    String role = scanner.nextLine();

    if (role.equalsIgnoreCase("Admin")) {
        switchToView("admin-view.fxml");
    } else if (role.equalsIgnoreCase("Student") || role.equalsIgnoreCase("faculty")) {
        switchToView("user-view.fxml");
        } else {
        System.exit(0);
    }
    }

    @FXML
    private void switchToView(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/project/" + fxmlFile));

            // Load the new FXML file
            Parent newRoot = fxmlLoader.load();

            // Create a new scene with the loaded FXML
            Scene newScene = new Scene(newRoot, 600, 400);
            stage.setTitle("Subject Information");

            // Set the new scene on the stage and display it
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally: display an alert or handle the error appropriately
        }
    }
*/

    /*
    @FXML
    void editSubjects() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/project/edit-subjects.fxml"));

        // Create a new stage (window)
        Stage newStage = new Stage();
        newStage.setTitle("Edit Subjects");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @FXML
    void createSubjects(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/project/create-subjects.fxml"));

        // Create a new stage (window)
        Stage newStage = new Stage();
        newStage.setTitle("Create Subjects");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @FXML
    void deleteSubjects(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/project/delete-subjects.fxml"));

        // Create a new stage (window)
        Stage newStage = new Stage();
        newStage.setTitle("Delete Subjects");
        newStage.setScene(new Scene(root));
        newStage.show();
    }


    // CREATE: Open dialog to add new subject
    @FXML
    private void openCreateDialog(ActionEvent event) throws IOException {
        openSubjectDialog(null);
    }

    // Reusable dialog for CREATE and EDIT
    @FXML
    private void openSubjectDialog(Subjects subject) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/subject-dialog.fxml"));
        Parent root = loader.load();

        // Get the dialog's controller
        SubjectDialogController dialogController = loader.getController();
        dialogController.setSubject(subject); // Pass data for editing

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Block parent window
        stage.showAndWait();

        // Refresh table after dialog closes
        if (dialogController.isSaved()) {
            if (subject == null) { // CREATE
                subjectsData.add(dialogController.getSubject());
            } else { // EDIT
                subjectsTable.refresh();
            }
        }
    }
    */

/*
    @FXML
    private TableView<Subjects> tableView;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;

    // Initialize the table when the FXML loads
    @FXML
    public void initialize() {
        // Link columns to the model's properties
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Subject Name"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("Subject Code"));

        // Add sample data
        ObservableList<Subjects> data = FXCollections.observableArrayList(
                new Subjects("Mathematics", "MATH101"),
                new Subjects("History", "HIST101")
        );
        tableView.setItems(data);
    }
    */

