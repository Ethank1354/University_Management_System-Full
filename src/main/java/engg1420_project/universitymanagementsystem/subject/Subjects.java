// Anthony
//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

public class Subjects {
    private final SimpleStringProperty name;
    private final SimpleStringProperty code;

    public Subjects(String name, String code) {
        this.name = new SimpleStringProperty(name);
        this.code = new SimpleStringProperty(code);
    }

    // Property getters (critical for TableView updates)
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleStringProperty codeProperty() { return code; }

    // Standard getters/setters
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public String getCode() { return code.get(); }
    public void setCode(String value) { code.set(value); }
}
