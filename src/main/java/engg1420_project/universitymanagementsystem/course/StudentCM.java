//Mateo
package engg1420_project.universitymanagementsystem.course;


import javafx.beans.property.*;

public class StudentCM {
    private final StringProperty studentID;
    private final StringProperty name;
    private final StringProperty address;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty academicLevel;
    private final StringProperty currentSemester; // Changed to StringProperty

    public StudentCM(String studentID, String name, String address, String phone, String email, String academicLevel, String currentSemester) { // Updated constructor
        this.studentID = new SimpleStringProperty(studentID);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.academicLevel = new SimpleStringProperty(academicLevel);
        this.currentSemester = new SimpleStringProperty(currentSemester); // Initialized as StringProperty
    }

    public String getStudentID() {
        return studentID.get();
    }

    public StringProperty studentIDProperty() {
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

    public String getCurrentSemester() { return currentSemester.get(); }

    public StringProperty currentSemesterProperty() {
        return currentSemester;
    }
}