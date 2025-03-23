

//package com.example.project1;
package engg1420_project.universitymanagementsystem.subject;

import javafx.beans.property.SimpleStringProperty;

public class Subjects {
    private SimpleStringProperty name;
    private SimpleStringProperty code;

    public Subjects(String name, String code) {
        this.name = new SimpleStringProperty(name);
        this.code = new SimpleStringProperty(code);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return this.name.get();
    }

    public String getCode() {
        return this.code.get();
    }

    public SimpleStringProperty nameProperty() {
        return this.name;
    }

    public SimpleStringProperty codeProperty() {
        return this.code;
    }
}
