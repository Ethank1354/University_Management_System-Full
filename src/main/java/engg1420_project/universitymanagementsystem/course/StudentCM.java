//package com.example.project;
package engg1420_project.universitymanagementsystem.course;


import javafx.beans.property.*;

public class StudentCM {
    private final IntegerProperty studentID;
    private final StringProperty name;
    private final StringProperty address;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty academicLevel;
    private final IntegerProperty currentSemester;

    public StudentCM(int studentID, String name, String address, String phone, String email, String academicLevel, int currentSemester) {
        this.studentID = new SimpleIntegerProperty(studentID);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.academicLevel = new SimpleStringProperty(academicLevel);
        this.currentSemester = new SimpleIntegerProperty(currentSemester);
    }

    public int getStudentID() {
        return studentID.get();
    }

    public IntegerProperty studentIDProperty() {
        return studentID;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getAcademicLevel() {
        return academicLevel.get();
    }

    public StringProperty academicLevelProperty() {
        return academicLevel;
    }

    public int getCurrentSemester() {
        return currentSemester.get();
    }

    public IntegerProperty currentSemesterProperty() {
        return currentSemester;
    }
}