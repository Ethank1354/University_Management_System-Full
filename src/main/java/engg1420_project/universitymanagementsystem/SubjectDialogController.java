

//package com.example.project1;
package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SubjectDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField codeField;
    private Subjects subject;

    public SubjectDialogController() {
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
        this.nameField.setText(subject.getName());
        this.codeField.setText(subject.getCode());
    }

    public void updateSubject() {
        if (this.subject != null) {
            this.subject.setName(this.nameField.getText());
            this.subject.setCode(this.codeField.getText());
        }

    }

    public Subjects getSubject() {
        return new Subjects(this.nameField.getText(), this.codeField.getText());
    }

    public boolean isInputValid() {
        return !this.nameField.getText().isEmpty() && !this.codeField.getText().isEmpty();
    }
}
