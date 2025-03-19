

//package com.example.project1;
package engg1420_project.universitymanagementsystem;

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
    private final ObservableList<Subjects> subjectsData = FXCollections.observableArrayList();

    public UserViewSubjectsController() {
    }

    @FXML
    public void initialize() {
        this.nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.codeColumn.setCellValueFactory(new PropertyValueFactory("code"));
        this.subjectsData.add(new Subjects("Mathematics", "MATH101"));
        this.subjectsData.add(new Subjects("Computer Science", "COMP200"));
        this.subjectsData.add(new Subjects("Physics", "PHYS150"));
        this.subjectsTable.setItems(this.subjectsData);
    }

    public void addSubject(String name, String code) {
        this.subjectsData.add(new Subjects(name, code));
    }
}
