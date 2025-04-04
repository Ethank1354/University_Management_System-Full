// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.util.List;
import java.sql.SQLException;

public class UserViewSubjectsController {

    @FXML
    private TableView<Subjects> subjectsTable;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;
    @FXML
    private TextField searchField;

    private DatabaseManager dbManager;
    private ObservableList<Subjects> subjectsData = FXCollections.observableArrayList();
    private FilteredList<Subjects> filteredSubjects;

    public UserViewSubjectsController() {
        // Initialize database connection
        dbManager = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());
    }

    @FXML
    public void initialize() {
        // Initialize filtered list
        filteredSubjects = new FilteredList<>(subjectsData, p -> true);
        subjectsTable.setItems(filteredSubjects); // Bind TableView to filtered list

        // Configure table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());


        // Add search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    updateFilteredSubjects(newValue);
        });

        // Load data from database
        loadSubjectsFromDatabase();
    }

    private void updateFilteredSubjects(String searchText) {
        filteredSubjects.setPredicate(subject -> {
            if (searchText == null || searchText.isEmpty()) return true;

            String lowerCaseFilter = searchText.toLowerCase();
            String subjectName = subject.getName().toLowerCase();
            String subjectCode = subject.getCode().toLowerCase();

            // Match if name or code STARTS WITH the search text
            return subjectName.startsWith(lowerCaseFilter)
                    || subjectCode.startsWith(lowerCaseFilter);
        });
    }

    private void loadSubjectsFromDatabase() {
        subjectsData.clear();
        try {
            // Get data from database
            List<String> names = dbManager.getColumnValues("Subjects", "name");
            List<String> codes = dbManager.getColumnValues("Subjects", "code");

            // Populate observable list
            for(int i = 0; i < names.size(); i++) {
                subjectsData.add(new Subjects(names.get(i), codes.get(i)));
            }

            // Link data to table
            subjectsTable.setItems(subjectsData);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load subjects: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}