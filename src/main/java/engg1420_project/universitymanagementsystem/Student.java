//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Student Class
//This should be pretty obvious what things do
public class Student extends User {

    private DatabaseManager dbm;
    private String studentID;
    private List<String> studentMember = new ArrayList<>();

    private String phoneNumber;
    private String address;
    private String academicLvl;
    private String semster;
    private String thesis;
    private Double academicProgress;

    private String[] subjects;


    public Student(String studentID, DatabaseManager dbm) throws SQLException {
        this.dbm = dbm;
        this.studentMember = dbm.getRow("UMS_Data_Students ", "Student ID", studentID);
        this.studentID = studentMember.get(0);
        super.name = studentMember.get(1);
        this.address = studentMember.get(2);
        this.phoneNumber = studentMember.get(3);
        super.email = studentMember.get(4);
        this.academicLvl = studentMember.get(5);
        this.semster = studentMember.get(6);
        //Handle Subjects Registerd
        String[] subjectCol = {"Subject Registered"};

        subjects =  dbm.getFilteredValues("UMS_Data_Students ", subjectCol, "Student ID", studentID).get(0).split(", ");

        this.thesis = studentMember.get(8);
        this.academicProgress = Double.parseDouble(studentMember.get(10));
        super.password = studentMember.get(10);
        //Handle Profile Photo

    }

//Getter Methods
    public String getStudentID() {
        return studentID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getAcademicLvl() {
        return academicLvl;
    }

    public String getSemster() {
        return semster;
    }

    public String getThesis() {
        return thesis;
    }

    public double getAcademicProgress() {
        return academicProgress;
    }
    public String[] getSubjects() {
        return subjects;
    }

    //Setter Methods

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAcademicLvl(String academicLvl) {
        this.academicLvl = academicLvl;
    }

    public void setSemster(String semster) {
        this.semster = semster;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    public void setAcademicProgress(double academicProgress) {
        this.academicProgress = academicProgress;
    }

    public void updateSubjects(String[] subjects, int index) {
        this.subjects[index] = subjects[index];
    }

    public void updateStudent() throws SQLException {
        this.studentMember.set(0, this.studentID);
        this.studentMember.set(1, super.name);
        this.studentMember.set(2, this.address);
        this.studentMember.set(3, this.phoneNumber);
        this.studentMember.set(4, super.email);
        this.studentMember.set(5, this.academicLvl);
        this.studentMember.set(6, this.semster);
        //Update Subjects Registered
        this.studentMember.set(8, this.thesis);
        this.studentMember.set(9, this.academicProgress.toString());

        try {
            this.dbm.updateRowInTable("UMS_Data_Students", "Student ID", this.studentID, this.studentMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Update Profile Picture





}
