package engg1420_project.universitymanagementsystem.events;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddEventController {
    @FXML
    private TextField eventNameField;
    @FXML
    private TextField eventCodeField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField costField;
    @FXML
    private ImageView headerImageView;

    private File headerImageFile;
    private List<String> registeredStudents = new ArrayList<>();

    @FXML
    public void initialize() {
        // Default image
        headerImageView.setImage(new Image("default-image.png"));
    }

    @FXML
    private void handleAddEvent() {
        String eventName = eventNameField.getText();
        String eventCode = eventCodeField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String date = dateField.getValue().toString();
        String time = timeField.getText();
        int capacity = Integer.parseInt(capacityField.getText());
        double cost = Double.parseDouble(costField.getText());

        // Save event details (this can be expanded to store in a database)
        System.out.println("Event Added: " + eventName);
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        headerImageFile = fileChooser.showOpenDialog(null);
        if (headerImageFile != null) {
            headerImageView.setImage(new Image(headerImageFile.toURI().toString()));
        }
    }
}