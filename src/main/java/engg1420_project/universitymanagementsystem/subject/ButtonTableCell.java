// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;


import engg1420_project.universitymanagementsystem.subject.AdminViewSubjectsController;
import engg1420_project.universitymanagementsystem.subject.Subjects;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.io.IOException;

public class ButtonTableCell<S> extends TableCell<S, Void> {
    private final AdminViewSubjectsController controller;

    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");

    public ButtonTableCell(AdminViewSubjectsController controller) {
        this.controller = controller;

        editButton.setOnAction((event) -> {
            S rowData = getTableRow().getItem();
            if (rowData != null) {
                // Use the injected controller directly
                this.controller.handleEditSubject((Subjects) rowData);

            }
        });

        deleteButton.setOnAction((event) -> {
            S rowData = getTableRow().getItem();
            if (rowData != null) {
                this.controller.handleDeleteSubject((Subjects) rowData);
            }

        });
        setGraphic(new HBox(5, editButton, deleteButton));
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : new HBox((double)5.0F, new Node[]{editButton, deleteButton}));
    }

    public static <S> Callback<TableColumn<S, Void>, TableCell<S, Void>> forTableColumn(AdminViewSubjectsController controller) {
        {
            return param -> new ButtonTableCell<>(controller);
        }
    }
}
