// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserViewSubjectsController {
    @FXML
    private TableView<Subjects> subjectsTable;
    @FXML
    private TableColumn<Subjects, String> nameColumn;
    @FXML
    private TableColumn<Subjects, String> codeColumn;

    @FXML
    public void initialize() {
        // Bind columns to Subjects properties
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());

        subjectsTable.setItems(AdminViewSubjectsController.sharedSubjectsData);
    }
}
