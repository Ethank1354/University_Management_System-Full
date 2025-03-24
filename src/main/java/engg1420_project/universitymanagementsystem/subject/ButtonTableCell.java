// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;


import engg1420_project.universitymanagementsystem.subject.AdminViewSubjectsController;
import engg1420_project.universitymanagementsystem.subject.Subjects;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ButtonTableCell<S> extends TableCell<S, Void> {
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");

    public ButtonTableCell() {
        this.editButton.setOnAction((event) -> {
            S rowData = (S)this.getTableRow().getItem();
            if (rowData != null) {
                AdminViewSubjectsController controller = this.getController();
                if (controller != null) {
                    controller.handleEditSubject((Subjects)rowData);
                }
            }

        });
        this.deleteButton.setOnAction((event) -> {
            S rowData = (S)this.getTableRow().getItem();
            if (rowData != null) {
                AdminViewSubjectsController controller = this.getController();
                if (controller != null) {
                    controller.handleDeleteSubject((Subjects)rowData);
                }
            }

        });
        this.setGraphic(new HBox((double)5.0F, new Node[]{this.editButton, this.deleteButton}));
    }

    private AdminViewSubjectsController getController() {
        Node tableView = this.getTableView();
        Scene scene = tableView.getScene();
        if (scene != null) {
            Parent root = scene.getRoot();
            return (AdminViewSubjectsController)root.getProperties().get("controller");
        } else {
            return null;
        }
    }

    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        this.setGraphic(empty ? null : new HBox((double)5.0F, new Node[]{this.editButton, this.deleteButton}));
    }

    public static <S> Callback<TableColumn<S, Void>, TableCell<S, Void>> forTableColumn() {
        return (param) -> new ButtonTableCell();
    }
}
