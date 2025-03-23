package engg1420_project.universitymanagementsystem.faculty;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.projectClasses.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.example.engg1420facultymanagement.DatabaseManager;

public class Faculty extends User {

    private DatabaseManager dbm;
    private List<String> facultyMember = new ArrayList<>();
    private String facultyID;
    private String degree;
    private String researchInterest;
    private String officeLocation;
    private String coursesOffered;
    private String profilePhotoLocation;

    public Faculty(String facultyId, DatabaseManager dbm) throws SQLException {
        this.dbm = dbm;
        this.facultyMember = dbm.getRow("Faculties", "Faculty ID", facultyId);
        this.facultyID = facultyMember.get(0);
        super.name = facultyMember.get(1);
        this.degree = facultyMember.get(2);
        this.researchInterest = facultyMember.get(3);
        super.email = facultyMember.get(4);
        this.officeLocation = facultyMember.get(5);
        this.coursesOffered = facultyMember.get(6);
        super.password = facultyMember.get(7);

        List<String> columns = dbm.getColumnValues("Photos", "ID");

        for (String column : columns) {
            System.out.println(column);
        }

        List<String> photo = dbm.getRow("Photos", "ID", facultyID);

        for (String row : photo) {
            System.out.println("Row: " + row);
            System.out.println(row);
        }

        this.profilePhotoLocation = photo.get(1);
        System.out.println("Photo Location " + profilePhotoLocation);
    }

    public String getFacultyId() {
        return this.facultyID;
    }

    public String getDegree() {
        return this.degree;
    }

    public String getResearchInterest() {
        return this.researchInterest;
    }

    public String getOfficeLocation() {
        return this.officeLocation;
    }

    public String getCourses() {
        return this.coursesOffered;
    }

    public void setFacultyId(String facultyId) {
        this.facultyID = facultyId;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setResearchInterest(String researchInterest) {
        this.researchInterest = researchInterest;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    private void setCourses(String courses) {
        this.coursesOffered = courses;
    }

    public void addCourses(List<String> courses) {
        String newcourses = "";

        for(int i = 0; i < courses.size(); i++) {
            newcourses += courses.get(i) + ",";
        }
        setCourses(newcourses);
    }

    public String getProfilePhotoLocation(){
        return this.profilePhotoLocation;
    }

    public void setProfilePhotoLocation(String profilePhotoLocation){
        this.profilePhotoLocation = profilePhotoLocation;
    }

    public void updateInfo() {
        this.facultyMember.set(0, this.facultyID);
        this.facultyMember.set(1, super.name);
        this.facultyMember.set(2, this.degree);
        this.facultyMember.set(3, this.researchInterest);
        this.facultyMember.set(4, super.email);
        this.facultyMember.set(5, this.officeLocation);
        this.facultyMember.set(6, this.coursesOffered);
        this.facultyMember.set(7, super.password);

        try {
            this.dbm.updateRowInTable("Faculties", "Faculty ID", this.facultyID, this.facultyMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProfilePhoto() {
        try {
        List<String> photoStore = new ArrayList<>();
        photoStore.add(this.facultyID);
        photoStore.add(this.profilePhotoLocation);

            this.dbm.updateRowInTable("Photos", "ID", this.facultyID, photoStore);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
